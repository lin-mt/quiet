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
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void addRoleForTeamWithoutCheck(@NotNull Long teamId, @NotEmpty Set<Long> userIds, @NotNull String roleName) {
        List<QuietTeamUser> quietTeamUsers = teamUserService.findByTeamIdAndUserIds(teamId, userIds);
        if (CollectionUtils.isNotEmpty(quietTeamUsers)) {
            QuietRole role = roleService.findByRoleName(roleName);
            List<QuietTeamUserRole> newRoles = new ArrayList<>(quietTeamUsers.size());
            for (QuietTeamUser quietTeamUser : quietTeamUsers) {
                newRoles.add(new QuietTeamUserRole(quietTeamUser.getId(), role.getId()));
            }
            teamUserRoleRepository.saveAll(newRoles);
        }
    }
}
