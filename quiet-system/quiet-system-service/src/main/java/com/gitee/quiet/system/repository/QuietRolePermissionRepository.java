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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietRolePermission;
import org.springframework.stereotype.Repository;

/**
 * 查询角色-权限信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietRolePermissionRepository extends QuietRepository<QuietRolePermission> {

  /**
   * 根据角色ID 和权限ID 查询权限信息.
   *
   * @param roleId 角色ID
   * @param permissionId 权限ID
   * @return 角色-权限信息
   */
  QuietRolePermission getByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
