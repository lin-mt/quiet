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

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietTeamUser;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 团队成员 Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietTeamUserRepository extends QuietRepository<QuietTeamUser> {

    /**
     * 根据团队ID查询该团队下的所有成员
     *
     * @param teamId 团队ID
     * @return 该团队下的所有成员信息
     */
    List<QuietTeamUser> findAllByTeamId(Long teamId);

    /**
     * 查询该用户属于哪些团队
     *
     * @param userId 用户ID
     * @return 该用户所属的所有团队
     */
    List<QuietTeamUser> findAllByUserId(Long userId);

    /**
     * 根据用户ID删除该用户的所有团队信息
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据团队ID批量查询成员信息
     *
     * @param teamIds 团队ID集合
     * @return 成员信息
     */
    List<QuietTeamUser> findByTeamIdIsIn(Collection<? extends Serializable> teamIds);

    /**
     * 根据团队ID删除成员信息
     *
     * @param teamId 要删除的团队ID
     */
    void deleteByTeamId(Long teamId);

    /**
     * 根据团队ID和用户ID查询该团队下的团队-用户关系，如果用户ID不在团队中，则返回的集合中就没有该用户与团队的关系数据
     *
     * @param teamId  团队ID
     * @param userIds 要查询的用户ID
     * @return 在该团队中的团队和用户关系集合
     */
    List<QuietTeamUser> findAllByTeamIdAndUserIdIsIn(Long teamId, Collection<? extends Serializable> userIds);
}
