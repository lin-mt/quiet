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

package com.gitee.quite.system.repository;

import com.gitee.quite.system.entity.QuitePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询权限信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuitePermissionRepository extends JpaRepository<QuitePermission, Long> {
    
    /**
     * 根据应用名称查询权限配置信息
     *
     * @param applciatioonName 应用名称
     * @return 该应用的所有权限配置
     */
    List<QuitePermission> findAllByApplicationName(String applciatioonName);
    
    /**
     * 根据角色 ID 查询该角色下的所有权限配置信息
     *
     * @param roleId 角色ID
     * @return 该角色下的所有权限配置信息
     */
    List<QuitePermission> findAllByRoleId(Long roleId);
}
