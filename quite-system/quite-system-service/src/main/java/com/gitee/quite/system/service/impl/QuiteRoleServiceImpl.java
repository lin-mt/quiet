package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.exception.ServiceException;
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.service.QuiteRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteRoleServiceImpl implements QuiteRoleService {
    
    private final QuiteRoleRepository roleRepository;
    
    public QuiteRoleServiceImpl(QuiteRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public QuiteRole save(QuiteRole quiteRole) {
        checkRoleInfo(quiteRole);
        return roleRepository.save(quiteRole);
    }
    
    @Override
    public QuiteRole update(QuiteRole quiteRole) {
        checkRoleInfo(quiteRole);
        return roleRepository.saveAndFlush(quiteRole);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        roleRepository.deleteById(deleteId);
        return true;
    }
    
    private void checkRoleInfo(QuiteRole role) {
        role.setRoleName(role.getRoleName().toUpperCase());
        QuiteRole quiteRole = roleRepository.getByRoleName(role.getRoleName());
        if (quiteRole != null && !quiteRole.getId().equals(role.getId())) {
            throw new ServiceException("role.name.exist", role.getRoleName());
        }
        if (role.getParentId() != null) {
            if (!roleRepository.existsById(role.getParentId())) {
                throw new ServiceException("role.parent.id.no.exist", role.getParentId());
            }
        }
    }
}
