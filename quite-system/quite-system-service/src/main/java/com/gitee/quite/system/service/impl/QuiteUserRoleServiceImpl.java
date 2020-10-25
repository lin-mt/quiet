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

package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.exception.ServiceException;
import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.repository.QuiteUserRepository;
import com.gitee.quite.system.repository.QuiteUserRoleRepository;
import com.gitee.quite.system.service.QuiteUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户-角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteUserRoleServiceImpl implements QuiteUserRoleService {
    
    private final QuiteUserRepository userRepository;
    
    private final QuiteRoleRepository roleRepository;
    
    private final QuiteUserRoleRepository userRoleRepository;
    
    public QuiteUserRoleServiceImpl(QuiteUserRepository userRepository, QuiteRoleRepository roleRepository,
            QuiteUserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }
    
    @Override
    public QuiteUserRole saveOrUpdate(final QuiteUserRole userRole) {
        if (!userRepository.existsById(userRole.getUserId())) {
            throw new ServiceException("userRole.user.id.no.exist", userRole.getUserId());
        }
        if (!roleRepository.existsById(userRole.getRoleId())) {
            throw new ServiceException("userRole.role.id.no.exist", userRole.getRoleId());
        }
        Optional<QuiteUserRole> exist = userRoleRepository
                .findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
        exist.ifPresent(ur -> userRole.setId(ur.getId()));
        return userRoleRepository.save(userRole);
    }
    
    @Override
    public boolean delete(List<Long> ids) {
        return userRoleRepository.deleteByIdIn(ids);
    }
}
