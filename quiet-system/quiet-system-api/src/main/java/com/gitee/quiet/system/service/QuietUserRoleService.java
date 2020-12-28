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

import com.gitee.quiet.system.entity.QuietUserRole;

import java.util.Collection;
import java.util.List;

/**
 * 用户-角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietUserRoleService {
    
    /**
     * 用户新增或更新角色信息.
     *
     * @param quietUserRole 用户-角色信息
     * @return 用户-角色关联信息
     */
    QuietUserRole saveOrUpdate(QuietUserRole quietUserRole);
    
    /**
     * 批量删除用户的角色信息.
     *
     * @param ids 要删除的id集合
     */
    void deleteByIds(List<Long> ids);
    
    /**
     * 根据用户ID查询用户角色信息
     *
     * @param userId 用户ID
     * @return 用户角色信息
     */
    List<QuietUserRole> findByUserId(Long userId);
    
    /**
     * 删除用户的角色信息
     *
     * @param userId 要删除角色信息的用户ID
     */
    void deleteByUserId(Long userId);
    
    /**
     * 根据用户ID查询拥有的所有角色信息
     *
     * @param userIds 用户ID
     * @return 角色信息
     */
    List<QuietUserRole> findRolesByUserIds(Collection<Long> userIds);
}
