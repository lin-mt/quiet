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

import com.gitee.quite.system.entity.QuiteRolePermission;

/**
 * 角色-权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteRolePermissionService {
    
    /**
     * 新增角色-权限信息.
     *
     * @param rolePermission 角色-权限信息
     * @return 新增的角色-权限信息
     */
    QuiteRolePermission saveOrUpdate(QuiteRolePermission rolePermission);
    
    /**
     * 删除角色-权限信息.
     *
     * @param deleteId 要删除的角色-权限信息ID
     */
    void delete(Long deleteId);
}
