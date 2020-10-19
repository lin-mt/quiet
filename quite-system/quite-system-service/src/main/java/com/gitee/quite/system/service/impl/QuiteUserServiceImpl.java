package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.exception.ServiceException;
import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuiteUser;
import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.repository.QuiteUserRepository;
import com.gitee.quite.system.repository.QuiteUserRoleRepository;
import com.gitee.quite.system.service.QuiteUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quite.system.entity.QQuiteUser.quiteUser;

/**
 * 用户 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteUserServiceImpl implements QuiteUserService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuiteUserRepository userRepository;
    
    private final QuiteUserRoleRepository userRoleRepository;
    
    private final QuiteRoleRepository roleRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public QuiteUserServiceImpl(JPAQueryFactory jpaQueryFactory, QuiteUserRepository userRepository,
            QuiteUserRoleRepository userRoleRepository, QuiteRoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.jpaQueryFactory = jpaQueryFactory;
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
            throw new ServiceException("user.username.exist", quiteUser.getUsername());
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
            throw new ServiceException("user.username.exist", user.getUsername());
        }
        user.setSecretCode(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }
    
    @Override
    public QueryResults<QuiteUser> page(QuiteUser params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quiteUser.id, builder);
        Wus.NotBlankContains(params.getUsername(), quiteUser.username, builder);
        Wus.NotNullEq(params.getGender(), quiteUser.gender, builder);
        Wus.NotBlankContains(params.getPhoneNumber(), quiteUser.phoneNumber, builder);
        Wus.NotBlankContains(params.getEmailAddress(), quiteUser.emailAddress, builder);
        Wus.NotNullEq(params.getAccountExpired(), quiteUser.accountExpired, builder);
        Wus.NotNullEq(params.getAccountLocked(), quiteUser.accountLocked, builder);
        Wus.NotNullEq(params.getCredentialsExpired(), quiteUser.credentialsExpired, builder);
        return jpaQueryFactory.selectFrom(quiteUser).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
    }
}
