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

import com.gitee.quiet.system.entity.QuietTeamUser;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 团队成员信息 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietTeamUserService {

    /**
     * 根据用户ID删除该用户的团队信息
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据团队ID批量查询成员信息
     *
     * @param teamIds 团队ID
     * @return 团队ID跟成员信息的映射关系
     */
    Map<Long, List<QuietTeamUser>> mapTeamIdToTeamUsers(Set<Long> teamIds);

    /**
     * 批量查询团队中的成员信息
     *
     * @param teamIds 团队ID
     * @return 所有成员信息
     */
    List<QuietTeamUser> findAllUsersByTeamIds(Set<Long> teamIds);

    /**
     * 删除某个团队的所有成员信息
     *
     * @param teamId 团队ID
     */
    void deleteByTeamId(Long teamId);

    /**
     * 为团队批量添加成员
     *
     * @param teamId  团队ID
     * @param userIds 要添加的用户ID集合
     */
    void addUsers(Long teamId, Set<Long> userIds);

    /**
     * 根据团队ID和用户ID查询该团队下的团队-用户关系，如果用户ID不在团队中，则返回的集合中就没有该用户与团队的关系数据
     *
     * @param teamId  团队ID
     * @param userIds 要查询的用户ID
     * @return 在该团队中的团队和用户关系集合
     */
    List<QuietTeamUser> findByTeamIdAndUserIds(Long teamId, Set<Long> userIds);

    /**
     * 根据用户ID查询该用户下的所有团队信息
     *
     * @param userId 用户ID
     * @return 团队信息
     */
    List<QuietTeamUser> findAllByUserId(Long userId);
}
