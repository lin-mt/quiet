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
import com.gitee.quiet.common.service.util.Where;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietRoleRepository;
import com.gitee.quiet.system.repository.QuietUserRepository;
import com.gitee.quiet.system.repository.QuietUserRoleRepository;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
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

import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 用户 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietUserServiceImpl implements QuietUserService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietUserRepository userRepository;
    
    private final QuietUserRoleRepository userRoleRepository;
    
    private final QuietRoleRepository roleRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final QuietDepartmentUserService departmentUserService;
    
    private final QuietTeamUserService teamUserService;
    
    public QuietUserServiceImpl(JPAQueryFactory jpaQueryFactory, QuietUserRepository userRepository,
            QuietUserRoleRepository userRoleRepository, QuietRoleRepository roleRepository,
            PasswordEncoder passwordEncoder, QuietDepartmentUserService departmentUserService,
            QuietTeamUserService teamUserService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentUserService = departmentUserService;
        this.teamUserService = teamUserService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QuietUser user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<QuietUserRole> quietUserRoles = userRoleRepository.findByUserId(user.getId());
        if (!CollectionUtils.isEmpty(quietUserRoles)) {
            Set<Long> roleIds = quietUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
            user.setAuthorities(roleRepository.findAllById(roleIds));
        }
        return user;
    }
    
    @Override
    public QuietUser save(QuietUser quietUser) {
        if (userRepository.getByUsername(quietUser.getUsername()) != null) {
            throw new ServiceException("user.username.exist", quietUser.getUsername());
        }
        quietUser.setSecretCode(passwordEncoder.encode(quietUser.getSecretCode()));
        return userRepository.saveAndFlush(quietUser);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        // 删除用户-角色信息
        userRoleRepository.deleteByUserId(deleteId);
        // 删除部门-用户信息
        departmentUserService.deleteByUserId(deleteId);
        // 删除团队-用户信息
        teamUserService.deleteByUserId(deleteId);
        // TODO 删除跟用户相关的其他信息
        // 删除用户信息
        userRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public QuietUser update(QuietUser user) {
        QuietUser exist = userRepository.getByUsername(user.getUsername());
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new ServiceException("user.username.exist", user.getUsername());
        }
        user.setSecretCode(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }
    
    @Override
    public QueryResults<QuietUser> page(QuietUser params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Where.NotNullEq(params.getId(), quietUser.id, builder);
        Where.NotBlankContains(params.getUsername(), quietUser.username, builder);
        Where.NotNullEq(params.getGender(), quietUser.gender, builder);
        Where.NotBlankContains(params.getPhoneNumber(), quietUser.phoneNumber, builder);
        Where.NotBlankContains(params.getEmailAddress(), quietUser.emailAddress, builder);
        Where.NotNullEq(params.getAccountExpired(), quietUser.accountExpired, builder);
        Where.NotNullEq(params.getAccountLocked(), quietUser.accountLocked, builder);
        Where.NotNullEq(params.getCredentialsExpired(), quietUser.credentialsExpired, builder);
        return jpaQueryFactory.selectFrom(quietUser).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
    }
    
    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}