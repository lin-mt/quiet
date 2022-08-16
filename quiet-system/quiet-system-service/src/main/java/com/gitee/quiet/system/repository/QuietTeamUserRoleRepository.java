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
import com.gitee.quiet.system.entity.QuietTeamUserRole;
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
public interface QuietTeamUserRoleRepository extends QuietRepository<QuietTeamUserRole> {

  /**
   * 根据团队-用户ID批量查询团队成员的角色信息
   *
   * @param teamUserIds 团队-用户ID集合
   * @return 团队成员的角色信息
   */
  List<QuietTeamUserRole> findByTeamUserIdIsIn(Collection<? extends Serializable> teamUserIds);

  /**
   * 根据团队-用户ID批量删除用户的团队角色信息
   *
   * @param teamUserIds 团队-用户ID
   */
  void removeAllByTeamUserIdIsIn(Collection<? extends Serializable> teamUserIds);
}
