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

import com.gitee.quiet.system.entity.QuietTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * 团队 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietTeamService {

  /**
   * 分页查询团队信息
   *
   * @param params 查询条件
   * @param page 分页条件
   * @return 查询结果
   */
  Page<QuietTeam> page(QuietTeam params, Pageable page);

  /**
   * 根据团队ID查询团队信息
   *
   * @param id 团队ID
   * @return 团队信息
   */
  @Nullable
  QuietTeam findById(Long id);

  /**
   * 根据团队ID、成员ID、团队名称查询团队信息
   *
   * @param id 团队ID
   * @param teamUserId 成员ID
   * @param teamName 团队名称
   * @param ids 团队ID集合
   * @return 所有团队信息
   */
  List<QuietTeam> listTeams(Long id, Long teamUserId, String teamName, Set<Long> ids);
}
