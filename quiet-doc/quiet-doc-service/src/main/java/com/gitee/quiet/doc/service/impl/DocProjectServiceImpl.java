/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.MyDocProject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocProjectServiceImpl implements DocProjectService {
    
    public final DocProjectRepository projectRepository;
    
    public DocProjectServiceImpl(DocProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Override
    public MyDocProject getProjectByUserId(Long userId) {
        List<DocProject> docProjects = projectRepository.listAllByUserId(userId);
        MyDocProject myDocProject = new MyDocProject();
        List<DocProject> responsibleProjects = new ArrayList<>();
        List<DocProject> accessibleProjects = new ArrayList<>();
        docProjects.forEach(docProject -> {
            if (docProject.getPrincipal().equals(userId)) {
                responsibleProjects.add(docProject);
            } else {
                accessibleProjects.add(docProject);
            }
        });
        myDocProject.setResponsibleProjects(responsibleProjects);
        myDocProject.setAccessibleProjects(accessibleProjects);
        return myDocProject;
    }
    
    @Override
    public DocProject save(DocProject save) {
        checkInfo(save);
        return projectRepository.save(save);
    }
    
    @Override
    public DocProject update(DocProject update) {
        checkInfo(update);
        return projectRepository.saveAndFlush(update);
    }
    
    @Override
    public void delete(Long id) {
        projectRepository.findById(id).orElseThrow(() -> new ServiceException("project.id.not.exist", id));
        projectRepository.deleteById(id);
    }
    
    @Override
    public DocProject getById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ServiceException("project.id.not.exist", id));
    }
    
    private void checkInfo(DocProject docProject) {
        MyDocProject myDocProject = this.getProjectByUserId(docProject.getPrincipal());
        myDocProject.getResponsibleProjects().forEach(temp -> checkProjectName(docProject, temp));
        myDocProject.getAccessibleProjects().forEach(temp -> checkProjectName(docProject, temp));
    }
    
    private void checkProjectName(DocProject docProject, DocProject temp) {
        if (docProject.getName().equals(temp.getName()) || docProject.getId() != null) {
            throw new ServiceException("project.name.exist", temp.getName());
        }
    }
    
}
