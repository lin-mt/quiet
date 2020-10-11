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
