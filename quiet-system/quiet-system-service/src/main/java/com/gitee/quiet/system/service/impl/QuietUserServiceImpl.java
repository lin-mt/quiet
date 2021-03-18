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
import com.gitee.quiet.common.service.jpa.SelectBooleanBuilder;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietUserRepository;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 用户 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@DubboService
public class QuietUserServiceImpl implements QuietUserService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final PasswordEncoder passwordEncoder;
    
    private final QuietUserRepository userRepository;
    
    private final QuietUserRoleService userRoleService;
    
    private final QuietRoleService roleService;
    
    private final QuietDepartmentUserService departmentUserService;
    
    private final QuietTeamUserService teamUserService;
    
    public QuietUserServiceImpl(JPAQueryFactory jpaQueryFactory, PasswordEncoder passwordEncoder,
            QuietUserRepository userRepository, QuietUserRoleService userRoleService, QuietRoleService roleService,
            QuietDepartmentUserService departmentUserService, QuietTeamUserService teamUserService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.departmentUserService = departmentUserService;
        this.teamUserService = teamUserService;
    }
    
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        QuietUser user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<QuietUserRole> quietUserRoles = userRoleService.findByUserId(user.getId());
        if (CollectionUtils.isNotEmpty(quietUserRoles)) {
            Set<Long> roleIds = quietUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
            user.setAuthorities(roleService.findAllById(roleIds));
        }
        return user;
    }
    
    @Override
    public QuietUser save(@NotNull QuietUser quietUser) {
        if (userRepository.getByUsername(quietUser.getUsername()) != null) {
            throw new ServiceException("user.username.exist", quietUser.getUsername());
        }
        quietUser.setSecretCode(passwordEncoder.encode(quietUser.getSecretCode()));
        return userRepository.saveAndFlush(quietUser);
    }
    
    @Override
    public boolean delete(@NotNull Long deleteId) {
        // 删除用户-角色信息
        userRoleService.deleteByUserId(deleteId);
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
    public QuietUser update(@NotNull QuietUser user) {
        QuietUser exist = userRepository.getByUsername(user.getUsername());
        if (exist != null && !exist.getId().equals(user.getId())) {
            throw new ServiceException("user.username.exist", user.getUsername());
        }
        user.setSecretCode(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }
    
    @Override
    public QueryResults<QuietUser> page(QuietUser params, @NotNull Pageable page) {
        SelectBooleanBuilder select = SelectBuilder.booleanBuilder(params);
        QueryResults<QuietUser> results = select.from(jpaQueryFactory, quietUser, page);
        if (CollectionUtils.isNotEmpty(results.getResults())) {
            Set<Long> userIds = results.getResults().stream().map(QuietUser::getId).collect(Collectors.toSet());
            Map<Long, List<QuietRole>> userIdToRoleInfo = this.mapUserIdToRoleInfo(userIds);
            for (QuietUser user : results.getResults()) {
                user.setAuthorities(userIdToRoleInfo.get(user.getId()));
            }
        }
        return results;
    }
    
    @Override
    public boolean existsById(@NotNull Long userId) {
        return userRepository.existsById(userId);
    }
    
    @Override
    public Map<Long, List<QuietRole>> mapUserIdToRoleInfo(Collection<Long> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<QuietUserRole> allUserRoles = userRoleService.findRolesByUserIds(userIds);
            if (CollectionUtils.isNotEmpty(allUserRoles)) {
                Map<Long, List<QuietUserRole>> userIdToUserRoles = allUserRoles.stream()
                        .collect(Collectors.groupingBy(QuietUserRole::getUserId));
                Set<Long> roleIds = allUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
                Map<Long, QuietRole> roleIdToRoleInfo = roleService.findAllByIds(roleIds).stream()
                        .collect(Collectors.toMap(QuietRole::getId, val -> val));
                Map<Long, List<QuietRole>> result = new HashMap<>(userIds.size());
                for (Long userId : userIds) {
                    List<QuietUserRole> userRoles = userIdToUserRoles.get(userId);
                    result.put(userId, new ArrayList<>());
                    if (CollectionUtils.isNotEmpty(userRoles)) {
                        for (QuietUserRole userRole : userRoles) {
                            result.get(userId).add(roleIdToRoleInfo.get(userRole.getRoleId()));
                        }
                    }
                }
                return result;
            }
        }
        return Collections.emptyMap();
    }
    
    @Override
    public List<QuietUser> findByUserIds(@NotNull @NotEmpty Set<Long> userIds) {
        return userRepository.findByIdIsIn(userIds);
    }
    
    @Override
    public List<QuietUser> listUsersByName(String name, int limit) {
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isBlank(name)) {
            return new ArrayList<>();
        }
        builder.and(quietUser.username.contains(name).or(quietUser.fullName.contains(name)));
        JPAQuery<QuietUser> query = jpaQueryFactory.selectFrom(quietUser).where(builder);
        if (limit > 0) {
            query.limit(limit);
        }
        return query.fetchResults().getResults();
    }
}
