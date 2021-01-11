/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietTeamUserRole;
import com.gitee.quiet.system.repository.QuietTeamUserRoleRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户团队角色Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamUserRoleServiceImpl implements QuietTeamUserRoleService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietTeamUserRoleRepository teamUserRoleRepository;
    
    private final QuietTeamUserService teamUserService;
    
    private final QuietRoleService roleService;
    
    public QuietTeamUserRoleServiceImpl(JPAQueryFactory jpaQueryFactory,
            QuietTeamUserRoleRepository teamUserRoleRepository, QuietTeamUserService teamUserService,
            QuietRoleService roleService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamUserRoleRepository = teamUserRoleRepository;
        this.teamUserService = teamUserService;
        this.roleService = roleService;
    }
    
    @Override
    public List<QuietTeamUserRole> findByTeamUserIds(Set<Long> teamUserIds) {
        return teamUserRoleRepository.findByTeamUserIdIsIn(teamUserIds);
    }
    
    @Override
    public void deleteByTeamUserIds(Set<Long> teamUserIds) {
        teamUserRoleRepository.removeAllByTeamUserIdIsIn(teamUserIds);
    }
    
    @Override
    public void addRoleForTeam(@NotNull Long teamId, @NotEmpty Set<Long> userIds,
            @NotNull String roleName) {
        List<QuietTeamUser> teamUsers = teamUserService.findByTeamIdAndUserIds(teamId, userIds);
        Map<Long, QuietTeamUserRole> teamUserIdToRole = this
                .findByTeamUserIds(teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet())).stream()
                .collect(Collectors.toMap(QuietTeamUserRole::getTeamUserId, v -> v));
        if (CollectionUtils.isNotEmpty(teamUsers)) {
            QuietRole role = roleService.findByRoleName(roleName);
            List<QuietTeamUserRole> newRoles = new ArrayList<>(teamUsers.size());
            for (QuietTeamUser teamUser : teamUsers) {
                if (MapUtils.isNotEmpty(teamUserIdToRole)) {
                    QuietTeamUserRole exist = teamUserIdToRole.get(teamUser.getId());
                    if (exist != null && exist.getRoleId().equals(role.getId())) {
                        continue;
                    }
                }
                newRoles.add(new QuietTeamUserRole(teamUser.getId(), role.getId()));
            }
            teamUserRoleRepository.saveAll(newRoles);
        }
    }
}
