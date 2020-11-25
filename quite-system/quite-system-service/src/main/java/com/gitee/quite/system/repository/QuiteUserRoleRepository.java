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

import com.gitee.quite.system.entity.QuiteUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 查询用户-角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteUserRoleRepository extends JpaRepository<QuiteUserRole, Long> {
    
    /**
     * 根据用户ID查询用户ID跟角色的对应关系.
     *
     * @param userId 用户ID
     * @return 用户-角色信息
     */
    List<QuiteUserRole> findByUserId(Long userId);
    
    /**
     * 根据用户ID和角色ID查询是否该用户拥有该角色.
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户-角色对应信息
     */
    Optional<QuiteUserRole> findByUserIdAndRoleId(Long userId, Long roleId);
    
    /**
     * 根据ID批量删除用户-角色信息.
     *
     * @param ids 要删除的ID集合
     * @return true：删除成功
     */
    boolean deleteByIdIn(List<Long> ids);
    
    /**
     * 根据用户 ID 删除用户的所有角色信息
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}
