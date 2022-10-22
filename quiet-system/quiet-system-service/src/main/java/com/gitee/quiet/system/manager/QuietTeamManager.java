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
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    teamUserService.deleteByTeamId(quietTeam.getId());
    Set<Long> memberIds = new HashSet<>();
    // 保存成员信息，包括 PO 和 SM
    this.addMemberId(memberIds, members);
    this.addMemberId(memberIds, productOwners);
    this.addMemberId(memberIds, scrumMasters);
    // 添加团队成员信息
    teamUserService.addUsers(quietTeam.getId(), memberIds);
    // 添加 PO 角色
    if (CollectionUtils.isNotEmpty(productOwners)) {
      teamUserRoleService.addRoleForTeam(
          quietTeam.getId(),
          productOwners.stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ProductOwner);
    }
    // 添加 SM 角色
    if (CollectionUtils.isNotEmpty(scrumMasters)) {
      teamUserRoleService.addRoleForTeam(
          quietTeam.getId(),
          scrumMasters.stream().map(QuietUser::getId).collect(Collectors.toSet()),
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
}
