/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.dubbo.UserDubboService;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.MyDocProject;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietUser;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Project Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectServiceImpl implements DocProjectService {

    public static final String CACHE_NAME = "quiet:doc:project:info";

    public final DocProjectRepository projectRepository;

    private final UserDubboService userDubboService;

    @Override
    public MyDocProject getProjectByUserId(Long userId) {
        List<DocProject> docProjects = projectRepository.listAllByUserId(userId);
        MyDocProject myDocProject = new MyDocProject();
        List<DocProject> responsibleProjects = new ArrayList<>();
        List<DocProject> accessibleProjects = new ArrayList<>();
        Set<Long> userIds = Sets.newHashSet();
        docProjects.forEach(docProject -> {
            userIds.add(docProject.getPrincipal());
            userIds.addAll(docProject.getVisitorIds());
            if (docProject.getPrincipal().equals(userId)) {
                responsibleProjects.add(docProject);
            } else {
                accessibleProjects.add(docProject);
            }
        });
        Map<Long, QuietUser> userIdToInfo = userDubboService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, user -> user));
        docProjects.forEach(docProject -> {
            docProject.setPrincipalName(userIdToInfo.get(docProject.getPrincipal()).getFullName());
            if (CollectionUtils.isNotEmpty(docProject.getVisitorIds())) {
                for (Long visitorId : docProject.getVisitorIds()) {
                    docProject.getVisitors().add(userIdToInfo.get(visitorId));
                }
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
    @PreAuthorize("@HasDocProjectPermission.edit(#update.id)")
    @CacheEvict(cacheNames = CACHE_NAME, key = "#update.id")
    public DocProject update(DocProject update) {
        checkInfo(update);
        return projectRepository.saveAndFlush(update);
    }

    @Override
    @PreAuthorize("@HasDocProjectPermission.delete(#id)")
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void delete(Long id) {
        projectRepository.findById(id).orElseThrow(() -> new ServiceException("project.id.not.exist", id));
        projectRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("@HasDocProjectPermission.visit(#id)")
    @Cacheable(cacheNames = CACHE_NAME, key = "#id", condition = "#id != null ", sync = true)
    public DocProject getById(Long id) {
        DocProject docProject = projectRepository.findById(id)
            .orElseThrow(() -> new ServiceException("project.id.not.exist", id));
        Set<Long> userIds = Sets.newHashSet(docProject.getVisitorIds());
        userIds.add(docProject.getPrincipal());
        Map<Long, QuietUser> userIdToInfo = userDubboService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, user -> user));
        docProject.setPrincipalName(userIdToInfo.get(docProject.getPrincipal()).getFullName());
        if (CollectionUtils.isNotEmpty(docProject.getVisitorIds())) {
            for (Long visitorId : docProject.getVisitorIds()) {
                docProject.getVisitors().add(userIdToInfo.get(visitorId));
            }
        }
        return docProject;
    }

    private void checkInfo(DocProject docProject) {
        MyDocProject myDocProject = this.getProjectByUserId(docProject.getPrincipal());
        myDocProject.getResponsibleProjects().forEach(temp -> checkProjectName(docProject, temp));
        myDocProject.getAccessibleProjects().forEach(temp -> checkProjectName(docProject, temp));
    }

    private void checkProjectName(DocProject docProject, DocProject existProject) {
        if (!existProject.getId().equals(docProject.getId()) && docProject.getName().equals(existProject.getName())) {
            throw new ServiceException("project.name.exist", existProject.getName());
        }
    }

}
