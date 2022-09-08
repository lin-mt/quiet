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
    return projectRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("project.id.not.exist", id));
  }

  public boolean visit(Long projectId) {
    if (CurrentUserUtil.isAdmin()) {
      return true;
    }
    Long currentUserId = CurrentUserUtil.getId();
    DocProject docProject = getById(projectId);
    if (docProject.getMemberIds().contains(currentUserId)) {
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
