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
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.*;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietTeamManager {

  private final QuietTeamRepository teamRepository;
  private final QuietTeamUserService teamUserService;
  private final QuietTeamUserRoleService teamUserRoleService;
  private final QuietRoleService roleService;

  /**
   * 新增或更新团队信息
   *
   * @param team 团队信息
   * @param productOwners PO
   * @param scrumMasters SM
   * @param members 团队成员
   * @return 新增或更新后的团队信息
   */
  public QuietTeam saveOrUpdate(
      QuietTeam team,
      List<QuietUser> productOwners,
      List<QuietUser> scrumMasters,
      List<QuietUser> members) {
    QuietTeam exist = teamRepository.getByTeamName(team.getTeamName());
    if (exist != null && !exist.getId().equals(team.getId())) {
      throw new ServiceException("team.teamName.exist", team.getTeamName());
    }
    // 更新团队信息
    QuietTeam quietTeam = teamRepository.saveAndFlush(team);
    // 删除所有旧数据，包括团队成员信息、团队成员的角色信息
    List<QuietTeamUser> allUsers = teamUserService.findAllByTeamId(quietTeam.getId());
    if (CollectionUtils.isNotEmpty(allUsers)) {
      teamUserRoleService.deleteByTeamUserIds(
              allUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
      teamUserService.deleteByTeamId(quietTeam.getId());
    }
    Set<Long> memberIds = new HashSet<>();
    // 保存成员信息，包括 PO 和 SM
    this.addMemberId(memberIds, members);
    this.addMemberId(memberIds, productOwners);
    this.addMemberId(memberIds, scrumMasters);
    // 添加团队成员信息
    teamUserService.addUsers(quietTeam.getId(), memberIds);
    // 添加 PO 角色
    if (CollectionUtils.isNotEmpty(productOwners)) {
      this.addRoleForTeam(
          quietTeam.getId(),
          productOwners.stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ProductOwner);
    }
    // 添加 SM 角色
    if (CollectionUtils.isNotEmpty(scrumMasters)) {
      this.addRoleForTeam(
          quietTeam.getId(),
          scrumMasters.stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ScrumMaster);
    }
    return quietTeam;
  }

  /**
   * 为团队用户批量添加角色，不检查用户在团队中是否有该角色
   *
   * @param teamId 团队ID
   * @param userIds 要添加角色的用户ID集合
   * @param roleName 角色名称
   */
  public void addRoleForTeam(
          @NotNull Long teamId, @NotEmpty Set<Long> userIds, @NotNull String roleName) {
    List<QuietTeamUser> teamUsers = teamUserService.findByTeamIdAndUserIds(teamId, userIds);
    if (CollectionUtils.isEmpty(teamUsers)) {
      // 确保添加的用户ID都属于该团队
      return;
    }
    Map<String, QuietTeamUserRole> teamUserIdToRole =
            teamUserRoleService.findByTeamUserIds(
                            teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()))
                    .stream()
                    .collect(Collectors.toMap(this::buildTeamUserIdToRole, v -> v));
    QuietRole role = roleService.findByRoleName(roleName);
    List<QuietTeamUserRole> newRoles = new ArrayList<>(teamUsers.size());
    for (QuietTeamUser teamUser : teamUsers) {
      if (MapUtils.isNotEmpty(teamUserIdToRole)) {
        QuietTeamUserRole exist = teamUserIdToRole.get(buildTeamUserIdToRole(teamUser, role));
        if (exist != null && exist.getRoleId().equals(role.getId())) {
          continue;
        }
      }
      newRoles.add(new QuietTeamUserRole(teamUser.getId(), role.getId()));
    }
    if (CollectionUtils.isNotEmpty(newRoles)) {
      teamUserRoleService.saveAll(newRoles);
    }
  }

  private String buildTeamUserIdToRole(@NotNull QuietTeamUserRole teamUserRole) {
    return teamUserRole.getTeamUserId().toString() + "-" + teamUserRole.getRoleId().toString();
  }

  private String buildTeamUserIdToRole(@NotNull QuietTeamUser teamUser, @NotNull QuietRole role) {
    return teamUser.getId().toString() + "-" + role.getId().toString();
  }

  private void addMemberId(@NotNull Set<Long> memberIds, List<QuietUser> members) {
    if (CollectionUtils.isNotEmpty(members)) {
      for (QuietUser member : members) {
        memberIds.add(member.getId());
      }
    }
  }

  public void deleteTeam(@NotNull Long deleteId) {
    // 删除团队成员信息
    teamUserService.deleteByTeamId(deleteId);
    // 删除团队信息
    teamRepository.deleteById(deleteId);
  }
}
