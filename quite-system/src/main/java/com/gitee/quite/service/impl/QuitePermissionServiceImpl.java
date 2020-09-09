package com.gitee.quite.service.impl;

import com.gitee.quite.entity.QuitePermission;
import com.gitee.quite.repository.QuitePermissionRepository;
import com.gitee.quite.service.QuitePermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuitePermissionServiceImpl implements QuitePermissionService {
    
    private final QuitePermissionRepository permissionRepository;
    
    public QuitePermissionServiceImpl(QuitePermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
    
    @Override
    public QuitePermission saveOrUpdate(QuitePermission permission) {
        return permissionRepository.save(permission);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        permissionRepository.deleteById(deleteId);
        return true;
    }
    
}
