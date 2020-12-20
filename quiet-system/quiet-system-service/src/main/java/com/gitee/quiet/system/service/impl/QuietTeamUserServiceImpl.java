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

import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.repository.QuietTeamUserRepository;
import org.springframework.stereotype.Service;

/**
 * 团队成员信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamUserServiceImpl implements QuietTeamUserService {
    
    private final QuietTeamUserRepository teamUserRepository;
    
    public QuietTeamUserServiceImpl(QuietTeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }
    
    @Override
    public void deleteByUserId(Long userId) {
        teamUserRepository.deleteByUserId(userId);
    }
}