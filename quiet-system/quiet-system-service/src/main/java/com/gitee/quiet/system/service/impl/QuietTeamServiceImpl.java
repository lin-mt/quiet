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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.gitee.quiet.system.entity.QQuietTeam.quietTeam;
import static com.gitee.quiet.system.entity.QQuietTeamUser.quietTeamUser;

/**
 * 团队 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@DubboService
public class QuietTeamServiceImpl implements QuietTeamService {

  private final JPAQueryFactory jpaQueryFactory;
  private final QuietTeamRepository teamRepository;
  private final QuietTeamUserService teamUserService;

  public QuietTeamServiceImpl(
      JPAQueryFactory jpaQueryFactory,
      QuietTeamRepository teamRepository,
      QuietTeamUserService teamUserService) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.teamRepository = teamRepository;
    this.teamUserService = teamUserService;
  }

  @Override
  public Page<QuietTeam> page(QuietTeam params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return teamRepository.findAll(predicate, page);
  }

  @Override
  public void deleteTeam(@NotNull Long deleteId) {
    // TODO 删除团队中的成员信息
    // 删除团队成员信息
    teamUserService.deleteByTeamId(deleteId);
    // 删除团队信息
    teamRepository.deleteById(deleteId);
  }

  @Nullable
  @Override
  public QuietTeam findById(Long id) {
    if (id == null) {
      return null;
    }
    return teamRepository.findById(id).orElse(null);
  }

  @Override
  public List<QuietTeam> listTeams(Long id, Long teamUserId, String teamName, Set<Long> ids) {
    List<QuietTeam> teams = new ArrayList<>();
    if (id != null) {
      QuietTeam team = this.findById(id);
      if (team != null) {
        teams.add(team);
      }
      return teams;
    }
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .isIdEq(teamUserId, quietTeamUser.userId)
            .notBlankContains(teamName, quietTeam.teamName)
            .notEmptyIn(ids, quietTeam.id)
            .getPredicate();
    teams =
        jpaQueryFactory
            .selectFrom(quietTeam)
            .leftJoin(quietTeamUser)
            .on(quietTeam.id.eq(quietTeamUser.teamId))
            .where(where)
            .distinct()
            .fetch();
    return teams;
  }
}
