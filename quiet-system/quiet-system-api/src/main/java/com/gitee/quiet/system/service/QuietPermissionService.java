/*
 * Copyright 2020 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.system.service;

import com.gitee.quiet.common.service.security.UrlPermissionService;
import com.gitee.quiet.system.entity.QuietPermission;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    QueryResults<QuietPermission> page(QuietPermission params, Pageable page);
    
    /**
     * 根据角色 ID 查询该角色下的权限配置信息
     *
     * @param roleId 角色ID
     * @return 该角色下的所有权限配置信息
     */
    List<QuietPermission> listByRoleId(Long roleId);
}
