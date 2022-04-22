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

package com.gitee.quiet.doc.security;

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 校验是否有访问项目/修改项目的权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
@Component("HasDocProjectPermission")
public class HasDocProjectPermission {

    private final DocProjectRepository projectRepository;

    private final String CACHE_NAME = "quiet:doc:project:permission";

    @Cacheable(cacheNames = CACHE_NAME, key = "#id", condition = "#id != null ", sync = true)
    public DocProject getById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ServiceException("project.id.not.exist", id));
    }

    public boolean visit(Long projectId) {
        if (CurrentUserUtil.isAdmin()) {
            return true;
        }
        Long currentUserId = CurrentUserUtil.getId();
        DocProject docProject = getById(projectId);
        if (docProject.getVisitorIds().contains(currentUserId)) {
            return true;
        }
        return currentUserId.equals(docProject.getPrincipal());
    }

    public boolean edit(Long projectId) {
        if (CurrentUserUtil.isAdmin()) {
            return true;
        }
        return getById(projectId).getPrincipal().equals(CurrentUserUtil.getId());
    }

    public boolean delete(Long projectId) {
        if (CurrentUserUtil.isAdmin()) {
            return true;
        }
        return getById(projectId).getPrincipal().equals(CurrentUserUtil.getId());
    }
}
