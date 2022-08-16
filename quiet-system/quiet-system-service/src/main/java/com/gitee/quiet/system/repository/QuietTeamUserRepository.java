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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietTeamUser;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
   * @param teamId 团队ID
   * @param userIds 要查询的用户ID
   * @return 在该团队中的团队和用户关系集合
   */
  List<QuietTeamUser> findAllByTeamIdAndUserIdIsIn(
      Long teamId, Collection<? extends Serializable> userIds);
}
