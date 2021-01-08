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

import com.gitee.quiet.system.entity.QuietTeamUserRole;
import com.gitee.quiet.system.repository.QuietTeamUserRoleRepository;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 用户团队角色Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamUserRoleServiceImpl implements QuietTeamUserRoleService {
    
    private final QuietTeamUserRoleRepository teamUserRoleRepository;
    
    public QuietTeamUserRoleServiceImpl(QuietTeamUserRoleRepository teamUserRoleRepository) {
        this.teamUserRoleRepository = teamUserRoleRepository;
    }
    
    @Override
    public List<QuietTeamUserRole> findByTeamIds(Set<Long> teamIds) {
        return teamUserRoleRepository.findByTeamIdIsIn(teamIds);
    }
    
    @Override
    public void deleteByUserId(Long userId) {
        teamUserRoleRepository.deleteByUserId(userId);
    }
}
