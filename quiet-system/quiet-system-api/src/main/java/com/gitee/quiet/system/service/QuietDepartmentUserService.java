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

import com.gitee.quiet.system.entity.QuietDepartmentUser;
import java.util.List;
import java.util.Set;

/**
 * 部门成员信息 service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDepartmentUserService {

    /**
     * 根据部门ID获取该部门下的所有用户
     *
     * @param departmentId 部门ID
     * @return 该部门下的所有用户
     */
    List<QuietDepartmentUser> listAllByDepartmentId(Long departmentId);

    /**
     * 根据用户ID删除该用户的部门信息
     *
     * @param userId 用户信息
     */
    void deleteByUserId(Long userId);

    /**
     * 为部门批量添加用户
     *
     * @param departmentId 要添加的部门ID
     * @param userIds      部门新增的用户ID集合
     */
    void addUsers(Long departmentId, Set<Long> userIds);

    /**
     * 批量删除部门中的某些用户
     *
     * @param departmentId 要删除的用户所在的部门ID
     * @param userIds      要删除的用户ID
     */
    void removeUsers(Long departmentId, Set<Long> userIds);
}
