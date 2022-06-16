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

import com.gitee.quiet.system.entity.QuietRolePermission;

/**
 * 角色-权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietRolePermissionService {

    /**
     * 新增角色-权限信息.
     *
     * @param rolePermission 角色-权限信息
     * @return 新增的角色-权限信息
     */
    QuietRolePermission saveOrUpdate(QuietRolePermission rolePermission);

    /**
     * 删除角色-权限信息.
     *
     * @param deleteId 要删除的角色-权限信息ID
     */
    void delete(Long deleteId);
}
