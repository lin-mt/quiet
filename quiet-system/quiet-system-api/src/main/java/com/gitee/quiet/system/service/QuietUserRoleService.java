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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietUserRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 用户-角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietUserRoleService {

  /**
   * 用户新增或更新角色信息.
   *
   * @param quietUserRole 用户-角色信息
   * @return 用户-角色关联信息
   */
  QuietUserRole saveOrUpdate(QuietUserRole quietUserRole);

  /**
   * 批量删除用户的角色信息.
   *
   * @param ids 要删除的id集合
   */
  void deleteByIds(List<Long> ids);

  /**
   * 根据用户ID查询用户角色信息
   *
   * @param userId 用户ID
   * @return 用户角色信息
   */
  List<QuietUserRole> findByUserId(Long userId);

  /**
   * 删除用户的角色信息
   *
   * @param userId 要删除角色信息的用户ID
   */
  void deleteByUserId(Long userId);

  /**
   * 根据用户ID查询拥有的所有角色信息
   *
   * @param userIds 用户ID
   * @return 角色信息
   */
  List<QuietUserRole> findRolesByUserIds(Collection<Long> userIds);

  /**
   * 删除某个用户的某个角色
   *
   * @param userId 用户ID
   * @param roleId 角色ID
   */
  void deleteUserRole(Long userId, Long roleId);

  /**
   * 批量新增用户角色信息
   *
   * @param userRoles 要新增的数据
   */
  List<QuietUserRole> addRoles(List<QuietUserRole> userRoles);

  /**
   * 更新用户拥有的角色信息
   *
   * @param userId 用户ID
   * @param roleIds 用户拥有的所有角色信息
   */
  void updateRoles(Long userId, Set<Long> roleIds);
}
