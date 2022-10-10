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

import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

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
  private final QuietTeamUserRoleService teamUserRoleService;
  private final QuietUserService userService;

  public QuietTeamServiceImpl(
      JPAQueryFactory jpaQueryFactory,
      QuietTeamRepository teamRepository,
      QuietTeamUserService teamUserService,
      QuietTeamUserRoleService teamUserRoleService,
      QuietUserService userService) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.teamRepository = teamRepository;
    this.teamUserService = teamUserService;
    this.teamUserRoleService = teamUserRoleService;
    this.userService = userService;
  }

  @Override
  public Page<QuietTeam> page(QuietTeam params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return teamRepository.findAll(predicate, page);
  }

  @Override
  public QuietTeam saveOrUpdate(@NotNull QuietTeam team) {
    QuietTeam exist = teamRepository.getByTeamName(team.getTeamName());
    if (exist != null && !exist.getId().equals(team.getId())) {
      throw new ServiceException("team.teamName.exist", team.getTeamName());
    }
    // 更新团队信息
    QuietTeam quietTeam = teamRepository.saveAndFlush(team);
    // 删除所有旧数据，包括团队成员信息、团队成员的角色信息
    teamUserService.deleteByTeamId(quietTeam.getId());
    Set<Long> memberIds = new HashSet<>();
    // 保存成员信息，包括 PO 和 SM
    this.addMemberId(memberIds, team.getMembers());
    this.addMemberId(memberIds, team.getProductOwners());
    this.addMemberId(memberIds, team.getScrumMasters());
    // 添加团队成员信息
    teamUserService.addUsers(quietTeam.getId(), memberIds);
    // 添加 PO 角色
    if (CollectionUtils.isNotEmpty(team.getProductOwners())) {
      teamUserRoleService.addRoleForTeam(
          quietTeam.getId(),
          team.getProductOwners().stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ProductOwner);
    }
    // 添加 SM 角色
    if (CollectionUtils.isNotEmpty(team.getScrumMasters())) {
      teamUserRoleService.addRoleForTeam(
          quietTeam.getId(),
          team.getScrumMasters().stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ScrumMaster);
    }
    return quietTeam;
  }

  private void addMemberId(@NotNull Set<Long> memberIds, List<QuietUser> members) {
    if (CollectionUtils.isNotEmpty(members)) {
      for (QuietUser member : members) {
        memberIds.add(member.getId());
      }
    }
  }

  @Override
  public void deleteTeam(@NotNull Long deleteId) {
    // TODO 删除团队中的成员信息
    // 删除团队成员信息
    teamUserService.deleteByTeamId(deleteId);
    // 删除团队信息
    teamRepository.deleteById(deleteId);
  }

  @Override
  public List<QuietTeam> findAllByIds(Set<Long> ids) {
    return teamRepository.findAllById(ids);
  }

  @Override
  public List<QuietTeam> findAllByIdsIncludeMembers(Set<Long> ids) {
    List<QuietTeam> teams = teamRepository.findAllById(ids);
    List<QuietTeamUser> teamUsers = teamUserService.findAllUsersByTeamIds(ids);
    Map<Long, Set<Long>> teamIdToUserIds =
        teamUsers.stream()
            .collect(Collectors.groupingBy(QuietTeamUser::getTeamId))
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    e ->
                        e.getValue().stream()
                            .map(QuietTeamUser::getUserId)
                            .collect(Collectors.toSet())));
    Set<Long> userIds =
        teamUsers.stream().map(QuietTeamUser::getUserId).collect(Collectors.toSet());
    Map<Long, QuietUser> userIdToInfo =
        userService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, u -> u));
    for (QuietTeam team : teams) {
      List<QuietUser> members = new ArrayList<>();
      for (Long userId : teamIdToUserIds.get(team.getId())) {
        members.add(userIdToInfo.get(userId));
      }
      team.setMembers(members);
    }
    return teams;
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
