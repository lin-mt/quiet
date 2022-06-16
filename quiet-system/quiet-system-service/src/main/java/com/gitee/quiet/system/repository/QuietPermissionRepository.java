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
import com.gitee.quiet.system.entity.QuietPermission;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 查询权限信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietPermissionRepository extends QuietRepository<QuietPermission> {

    /**
     * 根据应用名称查询权限配置信息
     *
     * @param applicationName 应用名称
     * @return 该应用的所有权限配置
     */
    List<QuietPermission> findAllByApplicationName(String applicationName);

    /**
     * 根据角色 ID 查询该角色下的所有权限配置信息
     *
     * @param roleId 角色ID
     * @return 该角色下的所有权限配置信息
     */
    List<QuietPermission> findAllByRoleId(Long roleId);
}
