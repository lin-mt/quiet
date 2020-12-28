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

import com.gitee.quiet.system.repository.QuietRoleRepository;
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.util.Where;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.system.entity.QQuietRole.quietRole;

/**
 * 角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRoleServiceImpl implements QuietRoleService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietRoleRepository roleRepository;
    
    private final QuietPermissionService permissionService;
    
    private String rolePrefix = "ROLE_";
    
    public QuietRoleServiceImpl(JPAQueryFactory jpaQueryFactory,
            Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults, QuietRoleRepository roleRepository,
            QuietPermissionService permissionService) {
        this.jpaQueryFactory = jpaQueryFactory;
        grantedAuthorityDefaults.ifPresent(prefix -> rolePrefix = prefix.getRolePrefix());
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }
    
    @Override
    public QuietRole save(QuietRole quietRole) {
        checkRoleInfo(quietRole);
        return roleRepository.save(quietRole);
    }
    
    @Override
    public QuietRole update(QuietRole quietRole) {
        checkRoleInfo(quietRole);
        return roleRepository.saveAndFlush(quietRole);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        roleRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public List<QuietRole> findAllByIds(Collection<Long> ids) {
        return roleRepository.findAllById(ids);
    }
    
    @Override
    public QueryResults<QuietRole> page(QuietRole params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Where.NotNullEq(params.getId(), quietRole.id, builder);
        Where.NotNullEq(params.getParentId(), quietRole.parentId, builder);
        Where.NotBlankContains(params.getRoleName(), quietRole.roleName, builder);
        Where.NotBlankContains(params.getRoleCnName(), quietRole.roleCnName, builder);
        Where.NotBlankContains(params.getRemark(), quietRole.remark, builder);
        QueryResults<QuietRole> results = jpaQueryFactory.selectFrom(quietRole).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
        if (!results.getResults().isEmpty()) {
            Set<Long> parentIds = results.getResults().stream().map(QuietRole::getParentId)
                    .filter(parentId -> !Objects.isNull(parentId)).collect(Collectors.toSet());
            Map<Long, QuietRole> idToRoleInfo = roleRepository.findAllById(parentIds).stream()
                    .collect(Collectors.toMap(QuietRole::getId, role -> role));
            for (QuietRole role : results.getResults()) {
                if (role.getParentId() != null) {
                    role.setParentRoleName(idToRoleInfo.get(role.getParentId()).getRoleName());
                }
            }
        }
        return results;
    }
    
    @Override
    public void deleteRole(Long deleteId) {
        Optional<QuietRole> exist = roleRepository.findById(deleteId);
        if (exist.isEmpty()) {
            throw new ServiceException("role.not.exist");
        }
        List<QuietRole> children = roleRepository.findByParentIdIn(Collections.singleton(exist.get().getId()));
        if (CollectionUtils.isNotEmpty(children)) {
            throw new ServiceException("role.can.not.delete.has.children");
        }
        List<QuietPermission> permissions = permissionService.listByRoleId(deleteId);
        if (CollectionUtils.isNotEmpty(permissions)) {
            throw new ServiceException("role.can.not.delete.has.permission.config");
        }
        roleRepository.deleteById(deleteId);
    }
    
    @Override
    public boolean existsById(Long roleId) {
        return roleRepository.existsById(roleId);
    }
    
    @Override
    public List<QuietRole> tree() {
        List<QuietRole> allRole = roleRepository.findAll();
        Map<Long, QuietRole> roleIdToInfo = new HashMap<>();
        for (QuietRole role : allRole) {
            roleIdToInfo.put(role.getId(), role);
        }
        List<QuietRole> result = new ArrayList<>();
        for (QuietRole role : allRole) {
            if (role.getParentId() == null) {
                result.add(role);
            } else {
                roleIdToInfo.get(role.getParentId()).addChildren(role);
            }
        }
        return result;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> findAllById(Set<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        // TODO 添加缓存
        if (authorities == null || authorities.isEmpty()) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        Set<String> roleNames = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Set<QuietRole> reachableRoles = new HashSet<>();
        while (!roleNames.isEmpty()) {
            List<QuietRole> roles = roleRepository.findByRoleNameIn(roleNames);
            reachableRoles.addAll(roles.stream().peek(r -> r.setRoleName(rolePrefix + r.getRoleName()))
                    .collect(Collectors.toSet()));
            Set<Long> parentIds = roles.stream().map(QuietRole::getId).collect(Collectors.toSet());
            roleNames = roleRepository.findByParentIdIn(parentIds).stream().map(QuietRole::getRoleName)
                    .collect(Collectors.toSet());
        }
        return reachableRoles;
    }
    
    private void checkRoleInfo(QuietRole role) {
        QuietRole quietRole = roleRepository.getByRoleName(role.getRoleName());
        if (quietRole != null && !quietRole.getId().equals(role.getId())) {
            throw new ServiceException("role.name.exist", role.getRoleName());
        }
        if (role.getParentId() != null) {
            if (!roleRepository.existsById(role.getParentId())) {
                throw new ServiceException("role.parent.id.no.exist", role.getParentId());
            }
        }
    }
}
