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

package com.gitee.quite.system.service.impl;

import com.gitee.quite.common.service.exception.ServiceException;
import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.repository.QuiteRoleRepository;
import com.gitee.quite.system.service.QuitePermissionService;
import com.gitee.quite.system.service.QuiteRoleService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quite.system.entity.QQuiteRole.quiteRole;

/**
 * 角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteRoleServiceImpl implements QuiteRoleService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuiteRoleRepository roleRepository;
    
    private final QuitePermissionService permissionService;
    
    private String rolePrefix = "ROLE_";
    
    public QuiteRoleServiceImpl(JPAQueryFactory jpaQueryFactory,
            Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults, QuiteRoleRepository roleRepository,
            QuitePermissionService permissionService) {
        this.jpaQueryFactory = jpaQueryFactory;
        grantedAuthorityDefaults.ifPresent(prefix -> rolePrefix = prefix.getRolePrefix());
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
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
    
    @Override
    public List<QuiteRole> findAllByIds(Collection<Long> ids) {
        return roleRepository.findAllById(ids);
    }
    
    @Override
    public QueryResults<QuiteRole> page(QuiteRole params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quiteRole.id, builder);
        Wus.NotNullEq(params.getParentId(), quiteRole.parentId, builder);
        Wus.NotBlankContains(params.getRoleName(), quiteRole.roleName, builder);
        Wus.NotBlankContains(params.getRoleCnName(), quiteRole.roleCnName, builder);
        Wus.NotBlankContains(params.getRemarks(), quiteRole.remarks, builder);
        QueryResults<QuiteRole> results = jpaQueryFactory.selectFrom(quiteRole).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
        if (!results.getResults().isEmpty()) {
            Set<Long> parentIds = results.getResults().stream().filter(role -> !Objects.isNull(role.getParentId()))
                    .map(QuiteRole::getParentId).collect(Collectors.toSet());
            Map<Long, QuiteRole> idToRoleInfo = roleRepository.findAllById(parentIds).stream()
                    .collect(Collectors.toMap(QuiteRole::getId, role -> role));
            for (QuiteRole role : results.getResults()) {
                if (role.getParentId() != null) {
                    role.setParentRoleName(idToRoleInfo.get(role.getParentId()).getRoleName());
                }
            }
        }
        return results;
    }
    
    @Override
    public void deleteRole(Long deleteId) {
        Optional<QuiteRole> exist = roleRepository.findById(deleteId);
        if (exist.isEmpty()) {
            throw new ServiceException("role.not.exist");
        }
        List<QuiteRole> children = roleRepository.findByParentIdIn(Collections.singleton(exist.get().getId()));
        if (CollectionUtils.isNotEmpty(children)) {
            throw new ServiceException("role.can.not.delete.has.children");
        }
        List<QuitePermission> permissions = permissionService.listByRoleId(deleteId);
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
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        // TODO 添加缓存
        if (authorities == null || authorities.isEmpty()) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        Set<String> roleNames = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Set<QuiteRole> reachableRoles = new HashSet<>();
        while (!roleNames.isEmpty()) {
            List<QuiteRole> roles = roleRepository.findByRoleNameIn(roleNames);
            reachableRoles.addAll(roles.stream().peek(r -> r.setRoleName(rolePrefix + r.getRoleName()))
                    .collect(Collectors.toSet()));
            Set<Long> parentIds = roles.stream().map(QuiteRole::getId).collect(Collectors.toSet());
            roleNames = roleRepository.findByParentIdIn(parentIds).stream().map(QuiteRole::getRoleName)
                    .collect(Collectors.toSet());
        }
        return reachableRoles;
    }
    
    private void checkRoleInfo(QuiteRole role) {
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
