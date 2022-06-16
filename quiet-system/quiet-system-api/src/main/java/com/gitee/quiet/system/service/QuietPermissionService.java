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

import com.gitee.quiet.service.security.UrlPermissionService;
import com.gitee.quiet.system.entity.QuietPermission;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietPermissionService extends UrlPermissionService {

    /**
     * 新增或更新权限信息.
     *
     * @param quietPermission 新增或更新的权限信息
     * @return 新增或更新的权限信息
     */
    QuietPermission saveOrUpdate(QuietPermission quietPermission);

    /**
     * 删除权限信息.
     *
     * @param deleteId 要删除的权限信息的ID
     * @return 删除的权限信息
     */
    QuietPermission delete(Long deleteId);

    /**
     * 分页查询权限配置信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    Page<QuietPermission> page(QuietPermission params, Pageable page);

    /**
     * 根据角色 ID 查询该角色下的权限配置信息
     *
     * @param roleId 角色ID
     * @return 该角色下的所有权限配置信息
     */
    List<QuietPermission> listByRoleId(Long roleId);
}
