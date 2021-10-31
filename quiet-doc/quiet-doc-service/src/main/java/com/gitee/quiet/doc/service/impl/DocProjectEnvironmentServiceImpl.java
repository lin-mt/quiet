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
 *
 */

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.doc.repository.DocProjectEnvironmentRepository;
import com.gitee.quiet.doc.service.DocProjectEnvironmentService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目环境服务实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectEnvironmentServiceImpl implements DocProjectEnvironmentService {

    private final DocProjectEnvironmentRepository repository;

    @Override
    public List<DocProjectEnvironment> listByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId);
    }

    @Override
    public DocProjectEnvironment save(DocProjectEnvironment save) {
        checkInfo(save);
        return repository.save(save);
    }

    @Override
    public DocProjectEnvironment update(DocProjectEnvironment update) {
        checkInfo(update);
        return repository.save(update);
    }
    
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> new ServiceException("projectEnvironment.id.not.exist", id));
        repository.deleteById(id);
    }
    
    private void checkInfo(DocProjectEnvironment entity) {
        DocProjectEnvironment exist = repository.findByProjectIdAndName(entity.getProjectId(), entity.getName());
        if (exist != null && !exist.getId().equals(entity.getId())) {
            throw new ServiceException("projectEnvironment.name.exist", entity.getProjectId().toString(), entity.getName());
        }
    }
}
