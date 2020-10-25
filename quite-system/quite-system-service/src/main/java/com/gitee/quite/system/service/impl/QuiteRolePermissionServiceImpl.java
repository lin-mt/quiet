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

import com.gitee.quite.system.entity.QuiteRolePermission;
import com.gitee.quite.system.repository.QuiteRolePermissionRepository;
import com.gitee.quite.system.service.QuiteRolePermissionService;
import org.springframework.stereotype.Service;

/**
 * 角色-权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteRolePermissionServiceImpl implements QuiteRolePermissionService {
    
    private final QuiteRolePermissionRepository rolePermissionRepository;
    
    public QuiteRolePermissionServiceImpl(QuiteRolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }
    
    @Override
    public QuiteRolePermission saveOrUpdate(QuiteRolePermission rolePermission) {
        QuiteRolePermission exist = rolePermissionRepository
                .getByRoleIdAndPermissionId(rolePermission.getRoleId(), rolePermission.getPermissionId());
        if (exist != null) {
            rolePermission.setId(exist.getId());
        }
        return rolePermissionRepository.save(rolePermission);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        rolePermissionRepository.deleteById(deleteId);
        return true;
    }
}
