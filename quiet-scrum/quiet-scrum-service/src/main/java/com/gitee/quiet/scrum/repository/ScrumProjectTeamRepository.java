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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 项目团队信息repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumProjectTeamRepository extends QuietRepository<ScrumProjectTeam> {

  /**
   * 根据团队ID查询团队负责的所有项目信息
   *
   * @param teamIds 团队ID集合
   * @return 团队负责的项目信息
   */
  List<ScrumProjectTeam> findAllByTeamIdIn(Set<Long> teamIds);

  /**
   * 根据项目ID查询所有项目团队信息
   *
   * @param projectIds 项目ID集合
   * @return 项目团队信息
   */
  List<ScrumProjectTeam> findAllByProjectIdIn(Set<Long> projectIds);

  /**
   * 根据项目ID删除项目的团队信息
   *
   * @param projectId 要删除团队信息的项目ID
   */
  void deleteAllByProjectId(Long projectId);
}
