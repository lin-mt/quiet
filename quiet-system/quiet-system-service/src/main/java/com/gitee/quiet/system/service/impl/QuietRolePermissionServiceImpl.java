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

import com.gitee.quiet.system.entity.QuietRolePermission;
import com.gitee.quiet.system.repository.QuietRolePermissionRepository;
import com.gitee.quiet.system.service.QuietRolePermissionService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 角色-权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRolePermissionServiceImpl implements QuietRolePermissionService {
    
    private final QuietRolePermissionRepository rolePermissionRepository;
    
    public QuietRolePermissionServiceImpl(QuietRolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }
    
    @Override
    public QuietRolePermission saveOrUpdate(@NotNull QuietRolePermission rolePermission) {
        QuietRolePermission exist = rolePermissionRepository
                .getByRoleIdAndPermissionId(rolePermission.getRoleId(), rolePermission.getPermissionId());
        if (exist != null) {
            rolePermission.setId(exist.getId());
        }
        return rolePermissionRepository.save(rolePermission);
    }
    
    @Override
    public void delete(@NotNull Long deleteId) {
        rolePermissionRepository.deleteById(deleteId);
    }
}
