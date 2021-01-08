/*
 * Copyright 2021 lin-mt@outlook.com
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

import com.gitee.quiet.system.entity.QuietTeamUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 用户团队角色Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietTeamUserRoleRepository extends JpaRepository<QuietTeamUserRole, Long> {
    
    /**
     * 根据团队ID批量查询团队成员的角色信息
     *
     * @param teamIds 团队ID集合
     * @return 团队成员的角色信息
     */
    List<QuietTeamUserRole> findByTeamIdIsIn(Collection<? extends Serializable> teamIds);
    
    /**
     * 根据用户ID删除用户的团队角色信息
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}
