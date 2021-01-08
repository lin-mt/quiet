/*
 * Copyright 2020 lin-mt@outlook.com
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
import org.springframework.stereotype.Service;

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
public class QuietTeamUserServiceImpl implements QuietTeamUserService {
    
    private final QuietTeamUserRepository teamUserRepository;
    
    private final QuietTeamUserRoleService teamUserRoleService;
    
    public QuietTeamUserServiceImpl(QuietTeamUserRepository teamUserRepository,
            QuietTeamUserRoleService teamUserRoleService) {
        this.teamUserRepository = teamUserRepository;
        this.teamUserRoleService = teamUserRoleService;
    }
    
    @Override
    public void deleteByUserId(Long userId) {
        List<QuietTeamUser> allTeamUser = teamUserRepository.findAllByUserId(userId);
        if (CollectionUtils.isNotEmpty(allTeamUser)) {
            teamUserRoleService
                    .deleteByTeamUserIds(allTeamUser.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
        }
        teamUserRepository.deleteByUserId(userId);
    }
    
    @Override
    public Map<Long, List<QuietTeamUser>> mapTeamIdToTeamUsers(Set<Long> teamIds) {
        return teamUserRepository.findByTeamIdIsIn(teamIds).stream()
                .collect(Collectors.groupingBy(QuietTeamUser::getTeamId));
    }
    
    @Override
    public List<QuietTeamUser> findAllUsersByTeamIds(Set<Long> teamIds) {
        return teamUserRepository.findByTeamIdIsIn(teamIds);
    }
}
