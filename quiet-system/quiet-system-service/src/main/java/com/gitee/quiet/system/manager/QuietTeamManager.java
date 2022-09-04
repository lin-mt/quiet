/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.manager;

import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.system.entity.*;
import com.gitee.quiet.system.service.*;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietTeamManager {

  private final QuietTeamService teamService;

  private final QuietTeamUserService teamUserService;

  private final QuietTeamUserRoleService teamUserRoleService;

  private final QuietRoleService roleService;

  private final QuietUserService userService;

  /**
   * 根据团队ID查询团队详情，包括团队成员、PM、SM的信息
   *
   * @param id 团队ID
   * @return 团队详细信息
   */
  @Nullable
  public QuietTeam getDetailById(Long id) {
    QuietTeam team = teamService.findById(id);
    if (team == null) {
      return null;
    }
    List<QuietTeamUser> teamUsers = teamUserService.findByTeamId(id);
    Set<Long> userIds =
        teamUsers.stream().map(QuietTeamUser::getUserId).collect(Collectors.toSet());
    List<QuietTeamUserRole> userTeamRoles =
        teamUserRoleService.findByTeamUserIds(
            teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
    Map<Long, List<QuietTeamUserRole>> teamUserIdToRoles =
        userTeamRoles.stream().collect(Collectors.groupingBy(QuietTeamUserRole::getTeamUserId));
    Map<Long, QuietUser> userIdToUserInfo =
        userService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, u -> u));
    QuietRole productOwner = roleService.findByRoleName(RoleNames.ProductOwner);
    QuietRole scrumMaster = roleService.findByRoleName(RoleNames.ScrumMaster);
    List<QuietUser> members = new ArrayList<>();
    List<QuietUser> teamProductOwners = new ArrayList<>();
    List<QuietUser> teamScrumMasters = new ArrayList<>();
    for (QuietTeamUser teamUser : teamUsers) {
      List<QuietTeamUserRole> teamUserRoles = teamUserIdToRoles.get(teamUser.getId());
      if (CollectionUtils.isNotEmpty(teamUserRoles)) {
        for (QuietTeamUserRole quietTeamUserRole : teamUserRoles) {
          if (quietTeamUserRole.getRoleId().equals(productOwner.getId())) {
            teamProductOwners.add(userIdToUserInfo.get(teamUser.getUserId()));
          }
          if (quietTeamUserRole.getRoleId().equals(scrumMaster.getId())) {
            teamScrumMasters.add(userIdToUserInfo.get(teamUser.getUserId()));
          }
        }
      }
      members.add(userIdToUserInfo.get(teamUser.getUserId()));
    }
    team.setMembers(members);
    team.setProductOwners(teamProductOwners);
    team.setScrumMasters(teamScrumMasters);
    return team;
  }
}
