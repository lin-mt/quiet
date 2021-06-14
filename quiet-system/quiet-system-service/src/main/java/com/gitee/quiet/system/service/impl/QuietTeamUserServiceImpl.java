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
    
    public QuietTeamUserServiceImpl(QuietTeamUserRepository teamUserRepository,
            @Lazy QuietTeamUserRoleService teamUserRoleService) {
        this.teamUserRepository = teamUserRepository;
        this.teamUserRoleService = teamUserRoleService;
    }
    
    @Override
    public void deleteByUserId(@NotNull Long userId) {
        List<QuietTeamUser> allTeamUser = teamUserRepository.findAllByUserId(userId);
        if (CollectionUtils.isNotEmpty(allTeamUser)) {
            teamUserRoleService
                    .deleteByTeamUserIds(allTeamUser.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
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
            teamUserRoleService
                    .deleteByTeamUserIds(allUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
            teamUserRepository.deleteByTeamId(teamId);
        }
    }
    
    @Override
    public void addUsers(@NotNull Long teamId, Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        Set<Long> allExistUserIds = this.findAllUsersByTeamIds(Set.of(teamId)).stream().map(QuietTeamUser::getUserId)
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
    public List<QuietTeamUser> findByTeamIdAndUserIds(@NotNull Long teamId, @NotNull @NotEmpty Set<Long> userIds) {
        return teamUserRepository.findAllByTeamIdAndUserIdIsIn(teamId, userIds);
    }
    
    @Override
    public List<QuietTeamUser> findAllByUserId(@NotNull Long userId) {
        return teamUserRepository.findAllByUserId(userId);
    }
}
