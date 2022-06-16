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

package com.gitee.quiet.service.security;

import java.util.List;

/**
 * Url 权限 Service. todo 添加获取应用权限的 Bean
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface UrlPermissionService {

    /**
     * 根据应用名称获取全部 Url 权限配置信息
     *
     * @param applicationName 应用名称
     * @return 该应用的Url配置信息
     */
    List<UrlPermission> listUrlPermission(String applicationName);
}
