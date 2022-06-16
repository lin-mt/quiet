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

import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietTeamUserRole;
import com.gitee.quiet.system.repository.QuietTeamUserRoleRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

/**
 * 用户团队角色Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamUserRoleServiceImpl implements QuietTeamUserRoleService {

    private final QuietTeamUserRoleRepository teamUserRoleRepository;

    private final QuietTeamUserService teamUserService;

    private final QuietRoleService roleService;

    public QuietTeamUserRoleServiceImpl(QuietTeamUserRoleRepository teamUserRoleRepository,
        QuietTeamUserService teamUserService, QuietRoleService roleService) {
        this.teamUserRoleRepository = teamUserRoleRepository;
        this.teamUserService = teamUserService;
        this.roleService = roleService;
    }

    @Override
    public List<QuietTeamUserRole> findByTeamUserIds(Set<Long> teamUserIds) {
        return teamUserRoleRepository.findByTeamUserIdIsIn(teamUserIds);
    }

    @Override
    public void deleteByTeamUserIds(@NotNull @NotEmpty Set<Long> teamUserIds) {
        teamUserRoleRepository.removeAllByTeamUserIdIsIn(teamUserIds);
    }

    @Override
    public void addRoleForTeam(@NotNull Long teamId, @NotEmpty Set<Long> userIds, @NotNull String roleName) {
        List<QuietTeamUser> teamUsers = teamUserService.findByTeamIdAndUserIds(teamId, userIds);
        if (CollectionUtils.isEmpty(teamUsers)) {
            // 确保添加的用户ID都属于该团队
            return;
        }
        Map<String, QuietTeamUserRole> teamUserIdToRole = this
            .findByTeamUserIds(teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet())).stream()
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
            teamUserRoleRepository.saveAll(newRoles);
        }
    }

    private String buildTeamUserIdToRole(@NotNull QuietTeamUserRole teamUserRole) {
        return teamUserRole.getTeamUserId().toString() + "-" + teamUserRole.getRoleId().toString();
    }

    private String buildTeamUserIdToRole(@NotNull QuietTeamUser teamUser, @NotNull QuietRole role) {
        return teamUser.getId().toString() + "-" + role.getId().toString();
    }
}
