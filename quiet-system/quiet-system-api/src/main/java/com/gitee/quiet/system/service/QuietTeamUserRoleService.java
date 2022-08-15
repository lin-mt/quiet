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
   * @param teamId 团队ID
   * @param userIds 要添加角色的用户ID集合
   * @param roleName 角色名称
   */
  void addRoleForTeam(Long teamId, Set<Long> userIds, String roleName);
}
