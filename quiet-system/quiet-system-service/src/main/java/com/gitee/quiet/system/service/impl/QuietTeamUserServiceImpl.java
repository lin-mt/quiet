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

import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.repository.QuietTeamUserRepository;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 团队成员信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@DubboService
public class QuietTeamUserServiceImpl implements QuietTeamUserService {

  private final QuietTeamUserRepository teamUserRepository;

  private final QuietTeamUserRoleService teamUserRoleService;

  public QuietTeamUserServiceImpl(
      QuietTeamUserRepository teamUserRepository,
      @Lazy QuietTeamUserRoleService teamUserRoleService) {
    this.teamUserRepository = teamUserRepository;
    this.teamUserRoleService = teamUserRoleService;
  }

  @Override
  public void deleteByUserId(@NotNull Long userId) {
    List<QuietTeamUser> allTeamUser = teamUserRepository.findAllByUserId(userId);
    if (CollectionUtils.isNotEmpty(allTeamUser)) {
      teamUserRoleService.deleteByTeamUserIds(
          allTeamUser.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
    }
    teamUserRepository.deleteByUserId(userId);
  }

  @Override
  public Map<Long, List<QuietTeamUser>> mapTeamIdToTeamUsers(@NotNull @NotEmpty Set<Long> teamIds) {
    return teamUserRepository.findByTeamIdIsIn(teamIds).stream()
        .collect(Collectors.groupingBy(QuietTeamUser::getTeamId));
  }

  @Override
  public List<QuietTeamUser> findAllUsersByTeamIds(@NotNull @NotEmpty Set<Long> teamIds) {
    return teamUserRepository.findByTeamIdIsIn(teamIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByTeamId(@NotNull Long teamId) {
    List<QuietTeamUser> allUsers = teamUserRepository.findAllByTeamId(teamId);
    if (CollectionUtils.isNotEmpty(allUsers)) {
      teamUserRoleService.deleteByTeamUserIds(
          allUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
      teamUserRepository.deleteByTeamId(teamId);
    }
  }

  @Override
  public void addUsers(@NotNull Long teamId, Set<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      return;
    }
    Set<Long> allExistUserIds =
        this.findAllUsersByTeamIds(Set.of(teamId)).stream()
            .map(QuietTeamUser::getUserId)
            .collect(Collectors.toSet());
    if (CollectionUtils.isNotEmpty(allExistUserIds)) {
      userIds.removeAll(allExistUserIds);
      if (userIds.isEmpty()) {
        return;
      }
    }
    List<QuietTeamUser> quietTeamUsers = new ArrayList<>(userIds.size());
    for (Long memberId : userIds) {
      quietTeamUsers.add(new QuietTeamUser(teamId, memberId));
    }
    teamUserRepository.saveAll(quietTeamUsers);
  }

  @Override
  public List<QuietTeamUser> findByTeamIdAndUserIds(
      @NotNull Long teamId, @NotNull @NotEmpty Set<Long> userIds) {
    return teamUserRepository.findAllByTeamIdAndUserIdIsIn(teamId, userIds);
  }

  @Override
  public List<QuietTeamUser> findAllByUserId(@NotNull Long userId) {
    return teamUserRepository.findAllByUserId(userId);
  }

  @Override
  public List<QuietTeamUser> findByTeamId(Long id) {
    return teamUserRepository.findAllByTeamId(id);
  }
}
