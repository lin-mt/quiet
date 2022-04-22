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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietTeamUserRole;
import java.util.List;
import java.util.Set;

/**
 * 用户团队角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietTeamUserRoleService {

    /**
     * 根据团队ID批量查询团队的角色信息
     *
     * @param teamIds 团队ID集合
     * @return 团队成员的角色信息
     */
    List<QuietTeamUserRole> findByTeamUserIds(Set<Long> teamIds);

    /**
     * 根据团队-用户ID批量删除用户的团队角色信息
     *
     * @param teamUserIds 团队-用户ID集合
     */
    void deleteByTeamUserIds(Set<Long> teamUserIds);

    /**
     * 为团队用户批量添加角色，不检查用户在团队中是否有该角色
     *
     * @param teamId   团队ID
     * @param userIds  要添加角色的用户ID集合
     * @param roleName 角色名称
     */
    void addRoleForTeam(Long teamId, Set<Long> userIds, String roleName);
}
