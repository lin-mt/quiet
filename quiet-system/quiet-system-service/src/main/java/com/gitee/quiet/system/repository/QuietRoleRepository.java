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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.system.entity.QuietRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 查询角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietRoleRepository extends JpaRepository<QuietRole, Long> {
    
    /**
     * 根据角色名称获取角色信息.
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    QuietRole getByRoleName(String roleName);
    
    /**
     * 根据角色名称批量查找角色信息
     *
     * @param roleNames 要查找的角色名称集合
     * @return 角色信息
     */
    List<QuietRole> findByRoleNameIn(Set<String> roleNames);
    
    /**
     * 根据 ID 查询子角色集合数据
     *
     * @param parentIds 父 ID 集合
     * @return 子角色集合信息
     */
    List<QuietRole> findByParentIdIn(Collection<Long> parentIds);
}
