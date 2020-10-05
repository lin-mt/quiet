package com.gitee.quite.system.service.impl;

import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.linmt.exception.ServiceException;
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
        if (!userRepository.existsById(userRole.getRoleId())) {
            throw new ServiceException("S0201", userRole.getUserId());
        }
        if (!roleRepository.existsById(userRole.getRoleId())) {
            throw new ServiceException("S0202", userRole.getRoleId());
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
