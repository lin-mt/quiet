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

import com.gitee.quiet.system.entity.QuietRole;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietRoleService extends RoleHierarchy {
    
    /**
     * 新增角色信息.
     *
     * @param quietRole 新增的角色信息
     * @return 新增后的角色信息
     */
    QuietRole save(QuietRole quietRole);
    
    /**
     * 更新角色信息.
     *
     * @param quietRole 要更新的角色信息
     * @return 更新后的角色信息
     */
    QuietRole update(QuietRole quietRole);
    
    /**
     * 删除角色信息.
     *
     * @param deleteId 要删除的角色ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
    
    /**
     * 根据 ID 批量查找角色信息
     *
     * @param ids ID 集合
     * @return 角色信息集合
     */
    List<QuietRole> findAllByIds(Collection<Long> ids);
    
    /**
     * 查询所有角色信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuietRole> page(QuietRole params, Pageable page);
    
    /**
     * 根据角色 ID 删除角色
     *
     * @param deleteId 要删除的角色 ID
     */
    void deleteRole(Long deleteId);
    
    /**
     * 判断该角色 ID 是否存在
     *
     * @param roleId 角色ID
     * @return true：存在，false：不存在
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean existsById(Long roleId);
    
    /**
     * 查询角色信息，以树形结构返回
     *
     * @return 树形结构的角色信息
     */
    List<QuietRole> tree();
    
    /**
     * 根据角色ID集合查询角色信息
     *
     * @param roleIds 角色ID集合
     * @return 角色信息
     */
    Collection<QuietRole> findAllById(Set<Long> roleIds);
    
    /**
     * 根据角色名称查询数据
     *
     * @param roleName 角色名称
     * @return 角色数据
     */
    QuietRole findByRoleName(String roleName);
}
