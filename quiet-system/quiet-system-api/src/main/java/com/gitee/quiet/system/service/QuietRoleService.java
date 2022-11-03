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

import com.gitee.quiet.system.entity.QuietRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietRoleService extends RoleHierarchy {

  /**
   * 新增角色信息.
   *
   * @param quietRole 新增的角色信息
   * @return 新增后的角色信息
   */
  QuietRole save(QuietRole quietRole);

  /**
   * 更新角色信息.
   *
   * @param quietRole 要更新的角色信息
   * @return 更新后的角色信息
   */
  QuietRole update(QuietRole quietRole);

  /**
   * 删除角色信息.
   *
   * @param deleteId 要删除的角色ID
   * @return true：删除成功
   */
  boolean delete(Long deleteId);

  /**
   * 根据 ID 批量查找角色信息
   *
   * @param ids ID 集合
   * @return 角色信息集合
   */
  List<QuietRole> findAllByIds(Collection<Long> ids);

  /**
   * 查询所有角色信息
   *
   * @param params 查询参数
   * @param page 分页参数
   * @return 查询结果
   */
  Page<QuietRole> page(QuietRole params, Pageable page);

  /**
   * 判断该角色 ID 是否存在
   *
   * @param roleId 角色ID
   * @return true：存在，false：不存在
   */
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  boolean existsById(Long roleId);

  /**
   * 查询角色信息，以树形结构返回
   *
   * @return 树形结构的角色信息
   */
  List<QuietRole> tree();

  /**
   * 根据角色ID集合查询角色信息
   *
   * @param roleIds 角色ID集合
   * @return 角色信息
   */
  List<QuietRole> findAllById(Set<Long> roleIds);

  /**
   * 根据角色名称查询数据
   *
   * @param roleName 角色名称
   * @return 角色数据
   */
  QuietRole findByRoleName(String roleName);

  @Override
  List<QuietRole> getReachableGrantedAuthorities(
      Collection<? extends GrantedAuthority> authorities);
}
