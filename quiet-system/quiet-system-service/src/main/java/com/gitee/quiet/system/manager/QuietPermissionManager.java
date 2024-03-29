/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.manager;

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.security.UrlPermission;
import com.gitee.quiet.service.security.UrlPermissionService;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.repository.QuietPermissionRepository;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.system.service.QuietRoleService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietPermissionManager implements UrlPermissionService {

  private final QuietPermissionRepository permissionRepository;
  private final QuietRoleService roleService;

  /**
   * 新增或更新权限信息.
   *
   * @param permission 新增或更新的权限信息
   * @return 新增或更新的权限信息
   */
  @CacheEvict(value = QuietPermissionService.CACHE_INFO, key = "#permission.applicationName")
  public QuietPermission saveOrUpdate(@NotNull QuietPermission permission) {
    if (!roleService.existsById(permission.getRoleId())) {
      throw new ServiceException("role.id.not.exist", permission.getRoleId());
    }
    return permissionRepository.saveAndFlush(permission);
  }

  @Override
  @Cacheable(value = QuietPermissionService.CACHE_INFO, key = "#applicationName")
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
