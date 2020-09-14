package com.gitee.quite.service.impl;

import com.gitee.quite.entity.QuiteUser;
import com.gitee.quite.entity.QuiteUserRole;
import com.gitee.linmt.exception.ServiceException;
import com.gitee.quite.repository.QuiteRoleRepository;
import com.gitee.quite.repository.QuiteUserRepository;
import com.gitee.quite.repository.QuiteUserRoleRepository;
import com.gitee.quite.service.QuiteUserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteUserServiceImpl implements QuiteUserService {
    
    private final QuiteUserRepository userRepository;
    
    private final QuiteUserRoleRepository userRoleRepository;
    
    private final QuiteRoleRepository roleRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public QuiteUserServiceImpl(QuiteUserRepository userRepository, QuiteUserRoleRepository userRoleRepository,
            QuiteRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QuiteUser user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<QuiteUserRole> quiteUserRoles = userRoleRepository.findByUserId(user.getId());
        if (!CollectionUtils.isEmpty(quiteUserRoles)) {
            Set<Long> roleIds = quiteUserRoles.stream().map(QuiteUserRole::getRoleId).collect(Collectors.toSet());
            user.setAuthorities(roleRepository.findAllById(roleIds));
        }
        return user;
    }
    
    @Override
    public QuiteUser save(QuiteUser quiteUser) {
        if (userRepository.getByUsername(quiteUser.getUsername()) != null) {
            throw new ServiceException("S0001", quiteUser.getUsername());
        }
        quiteUser.setSecretCode(passwordEncoder.encode(quiteUser.getSecretCode()));
        return userRepository.saveAndFlush(quiteUser);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        userRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public QuiteUser update(QuiteUser user) {
        QuiteUser exist = userRepository.getByUsername(user.getUsername());
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new ServiceException("S0001", user.getUsername());
        }
        return userRepository.saveAndFlush(user);
    }
    
    @Override
    public List<QuiteUser> page(QuiteUser params, Pageable page) {
        return userRepository.findAll();
    }
}
