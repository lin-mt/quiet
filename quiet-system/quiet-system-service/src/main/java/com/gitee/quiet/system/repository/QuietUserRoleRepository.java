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
import com.gitee.quiet.system.entity.QuietUserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 查询用户-角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietUserRoleRepository extends QuietRepository<QuietUserRole> {

  /**
   * 根据用户ID查询用户ID跟角色的对应关系.
   *
   * @param userId 用户ID
   * @return 用户-角色信息
   */
  List<QuietUserRole> findByUserId(Long userId);

  /**
   * 根据用户ID和角色ID查询是否该用户拥有该角色.
   *
   * @param userId 用户ID
   * @param roleId 角色ID
   * @return 用户-角色对应信息
   */
  Optional<QuietUserRole> findByUserIdAndRoleId(Long userId, Long roleId);

  /**
   * 根据ID批量删除用户-角色信息.
   *
   * @param ids 要删除的ID集合
   */
  void deleteByIdIn(List<Long> ids);

  /**
   * 根据用户 ID 删除用户的所有角色信息
   *
   * @param userId 用户ID
   */
  void deleteByUserId(Long userId);

  /**
   * 根据用户ID查询拥有的所有角色信息
   *
   * @param userIds 用户ID
   * @return 指定用户集合中拥有的所有角色信息
   */
  List<QuietUserRole> findByUserIdIn(Collection<Long> userIds);

  /**
   * 删除某个用户的某个角色
   *
   * @param userId 用户ID
   * @param roleId 角色ID
   */
  @Transactional
  void deleteByUserIdAndRoleId(Long userId, Long roleId);
}
