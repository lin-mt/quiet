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

package com.gitee.quite.system.service;

import com.gitee.quite.common.service.security.UrlPermissionService;
import com.gitee.quite.system.entity.QuitePermission;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuitePermissionService extends UrlPermissionService {
    
    /**
     * 新增或更新权限信息.
     *
     * @param quitePermission 新增或更新的权限信息
     * @return 新增或更新的权限信息
     */
    QuitePermission saveOrUpdate(QuitePermission quitePermission);
    
    /**
     * 删除权限信息.
     *
     * @param deleteId 要删除的权限信息的ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
    
    /**
     * 分页查询权限配置信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuitePermission> page(QuitePermission params, Pageable page);
    
    /**
     * 根据角色 ID 查询该角色下的权限配置信息
     *
     * @param roleId 角色ID
     * @return 该角色下的所有权限配置信息
     */
    List<QuitePermission> listByRoleId(Long roleId);
}
