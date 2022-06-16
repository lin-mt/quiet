/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
