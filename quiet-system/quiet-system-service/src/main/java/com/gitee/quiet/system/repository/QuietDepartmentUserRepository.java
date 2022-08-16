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
import com.gitee.quiet.system.entity.QuietDepartmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 部门成员信息 repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDepartmentUserRepository extends QuietRepository<QuietDepartmentUser> {

  /**
   * 根据部门ID查询用户信息
   *
   * @param departmentId 部门ID
   * @return 该部门下的用户信息
   */
  List<QuietDepartmentUser> findAllByDepartmentId(Long departmentId);

  /**
   * 根据用户查询该用户所属部门
   *
   * @param userId 用户ID
   * @return 用户ID所属部门信息
   */
  QuietDepartmentUser getByUserId(Long userId);

  /**
   * 根据用户ID删除该用户的部门信息
   *
   * @param userId 用户ID
   */
  void deleteByUserId(Long userId);

  /**
   * 批量删除某部门的用户
   *
   * @param departmentId 要删除的用户所在的部门ID
   * @param userIds 要删除的用户ID
   */
  void deleteAllByDepartmentIdAndUserIdIsIn(Long departmentId, Set<Long> userIds);
}
