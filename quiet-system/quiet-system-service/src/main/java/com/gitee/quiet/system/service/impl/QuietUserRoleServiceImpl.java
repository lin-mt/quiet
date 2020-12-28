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

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietUserRoleRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 用户-角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietUserRoleServiceImpl implements QuietUserRoleService {
    
    private final QuietUserRoleRepository userRoleRepository;
    
    private final QuietUserService userService;
    
    private final QuietRoleService roleService;
    
    public QuietUserRoleServiceImpl(QuietUserRoleRepository userRoleRepository, QuietUserService userService,
            QuietRoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.roleService = roleService;
    }
    
    @Override
    public QuietUserRole saveOrUpdate(final QuietUserRole userRole) {
        if (!userService.existsById(userRole.getUserId())) {
            throw new ServiceException("userRole.user.id.no.exist", userRole.getUserId());
        }
        if (!roleService.existsById(userRole.getRoleId())) {
            throw new ServiceException("userRole.role.id.no.exist", userRole.getRoleId());
        }
        Optional<QuietUserRole> exist = userRoleRepository
                .findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
        exist.ifPresent(ur -> userRole.setId(ur.getId()));
        return userRoleRepository.save(userRole);
    }
    
    @Override
    public void deleteByIds(List<Long> ids) {
        userRoleRepository.deleteByIdIn(ids);
    }
    
    @Override
    public List<QuietUserRole> findByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }
    
    @Override
    public void deleteByUserId(Long userId) {
        userRoleRepository.deleteByUserId(userId);
    }
}
