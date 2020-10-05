package com.gitee.quite.system.service.impl;

import com.gitee.linmt.exception.ServiceException;
import com.gitee.quite.system.entity.QuiteUser;
import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.repository.QuiteUserRepository;
import com.gitee.quite.system.repository.QuiteUserRoleRepository;
import com.gitee.quite.system.service.QuiteUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
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
    public QueryResults<QuiteUser> pageByEntity(QuiteUser params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params.getId() != null) {
            builder.and(quiteUser.id.eq(params.getId()));
        }
        if (StringUtils.isNoneBlank(params.getUsername())) {
            builder.and(quiteUser.username.contains(params.getUsername()));
        }
        if (StringUtils.isNoneBlank(params.getPhoneNumber())) {
            builder.and(quiteUser.phoneNumber.eq(params.getPhoneNumber()));
        }
        if (StringUtils.isNoneBlank(params.getEmailAddress())) {
            builder.and(quiteUser.emailAddress.eq(params.getEmailAddress()));
        }
        if (params.getAccountNonExpired() != null) {
            builder.and(quiteUser.accountNonExpired.eq(params.getAccountNonExpired()));
        }
        if (params.getAccountNonLocked() != null) {
            builder.and(quiteUser.accountNonLocked.eq(params.getAccountNonLocked()));
        }
        if (params.getCredentialsNonExpired() != null) {
            builder.and(quiteUser.credentialsNonExpired.eq(params.getCredentialsNonExpired()));
        }
        return jpaQueryFactory.selectFrom(quiteUser).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
}
