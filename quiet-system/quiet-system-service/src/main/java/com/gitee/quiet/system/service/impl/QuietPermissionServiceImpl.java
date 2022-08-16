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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.security.UrlPermission;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.repository.QuietPermissionRepository;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietPermissionServiceImpl implements QuietPermissionService {

  public static final String CACHE_INFO = "quiet:system:permission:info";

  private final QuietPermissionRepository permissionRepository;

  private final QuietRoleService roleService;

  public QuietPermissionServiceImpl(
      QuietPermissionRepository permissionRepository, @Lazy QuietRoleService roleService) {
    this.permissionRepository = permissionRepository;
    this.roleService = roleService;
  }

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#permission.applicationName")
  public QuietPermission saveOrUpdate(@NotNull QuietPermission permission) {
    if (!roleService.existsById(permission.getRoleId())) {
      throw new ServiceException("role.id.not.exist", permission.getRoleId());
    }
    return permissionRepository.saveAndFlush(permission);
  }

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#result.applicationName")
  public QuietPermission delete(@NotNull Long deleteId) {
    QuietPermission deleted = permissionRepository.getById(deleteId);
    permissionRepository.deleteById(deleteId);
    return deleted;
  }

  @Override
  public Page<QuietPermission> page(QuietPermission params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return permissionRepository.findAll(predicate, page);
  }

  @Override
  public List<QuietPermission> listByRoleId(@NotNull Long roleId) {
    return permissionRepository.findAllByRoleId(roleId);
  }

  @Override
  @Cacheable(value = CACHE_INFO, key = "#applicationName")
  public List<UrlPermission> listUrlPermission(@NotNull String applicationName) {
    List<QuietPermission> permissions =
        permissionRepository.findAllByApplicationName(applicationName);
    List<UrlPermission> urlPermissions = new ArrayList<>(permissions.size());
    if (!permissions.isEmpty()) {
      Set<Long> roleIds =
          permissions.stream().map(QuietPermission::getRoleId).collect(Collectors.toSet());
      Map<Long, String> roleIdToRoleName =
          roleService.findAllByIds(roleIds).stream()
              .collect(Collectors.toMap(QuietRole::getId, QuietRole::getRoleName));
      UrlPermission urlPermission;
      for (QuietPermission permission : permissions) {
        urlPermission = new UrlPermission();
        urlPermission.setUrlPattern(permission.getUrlPattern());
        urlPermission.setRequestMethod(permission.getRequestMethod());
        String roleName = roleIdToRoleName.get(permission.getRoleId());
        if (StringUtils.isBlank(roleName)) {
          throw new ServiceException("permission.roleId.notExist");
        }
        urlPermission.setRoleName(roleName);
        urlPermissions.add(urlPermission);
      }
    }
    return urlPermissions;
  }
}
