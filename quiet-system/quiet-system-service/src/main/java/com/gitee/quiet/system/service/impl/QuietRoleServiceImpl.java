/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.repository.QuietRoleRepository;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRoleServiceImpl implements QuietRoleService {

    private final QuietRoleRepository roleRepository;

    private final QuietPermissionService permissionService;

    private String rolePrefix = "ROLE_";

    public QuietRoleServiceImpl(Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults,
        QuietRoleRepository roleRepository, QuietPermissionService permissionService) {
        grantedAuthorityDefaults.ifPresent(prefix -> rolePrefix = prefix.getRolePrefix());
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
    }

    @Override
    public QuietRole save(@NotNull QuietRole quietRole) {
        checkRoleInfo(quietRole);
        return roleRepository.save(quietRole);
    }

    @Override
    public QuietRole update(@NotNull QuietRole quietRole) {
        checkRoleInfo(quietRole);
        return roleRepository.saveAndFlush(quietRole);
    }

    @Override
    public boolean delete(@NotNull Long deleteId) {
        roleRepository.deleteById(deleteId);
        return true;
    }

    @Override
    public List<QuietRole> findAllByIds(@NotNull @NotEmpty Collection<Long> ids) {
        return roleRepository.findAllById(ids);
    }

    @Override
    public Page<QuietRole> page(QuietRole params, @NotNull Pageable page) {
        BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
        Page<QuietRole> roles = roleRepository.findAll(predicate, page);
        if (!roles.isEmpty()) {
            Set<Long> parentIds = roles.getContent().stream().map(QuietRole::getParentId)
                .filter(parentId -> !Objects.isNull(parentId)).collect(Collectors.toSet());
            Map<Long, QuietRole> idToRoleInfo = roleRepository.findAllById(parentIds).stream()
                .collect(Collectors.toMap(QuietRole::getId, role -> role));
            for (QuietRole role : roles.getContent()) {
                if (role.getParentId() != null) {
                    role.setParentRoleName(idToRoleInfo.get(role.getParentId()).getRoleName());
                }
            }
        }
        return roles;
    }

    @Override
    public void deleteRole(@NotNull Long deleteId) {
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
    public boolean existsById(@NotNull Long roleId) {
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
    public List<QuietRole> findAllById(@NotNull @NotEmpty Set<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

    @NotNull
    @Override
    public QuietRole findByRoleName(@NotNull String roleName) {
        QuietRole quietRole = roleRepository.findByRoleName(roleName);
        if (quietRole == null) {
            throw new ServiceException("role.roleName.does.not.exist", roleName);
        }
        return quietRole;
    }

    @Override
    public List<QuietRole> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        // TODO 添加缓存
        if (CollectionUtils.isEmpty(authorities)) {
            return List.of();
        }
        Set<String> roleNames = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        List<QuietRole> reachableRoles = new ArrayList<>();
        while (!roleNames.isEmpty()) {
            List<QuietRole> roles = roleRepository.findByRoleNameIn(roleNames);
            reachableRoles.addAll(roles.stream().peek(r -> {
                if (StringUtils.isNoneBlank(r.getRoleName()) && !r.getRoleName().startsWith(rolePrefix)) {
                    r.setRoleName(rolePrefix + r.getRoleName());
                }
            }).collect(Collectors.toSet()));
            Set<Long> parentIds = roles.stream().map(QuietRole::getId).collect(Collectors.toSet());
            roleNames = roleRepository.findByParentIdIn(parentIds).stream().map(QuietRole::getRoleName)
                .collect(Collectors.toSet());
        }
        return reachableRoles;
    }

    private void checkRoleInfo(@NotNull QuietRole role) {
        QuietRole quietRole = roleRepository.findByRoleName(role.getRoleName());
        if (quietRole != null && !quietRole.getId().equals(role.getId())) {
            throw new ServiceException("role.roleName.exist", role.getRoleName());
        }
        if (role.getParentId() != null) {
            if (!roleRepository.existsById(role.getParentId())) {
                throw new ServiceException("role.parent.id.no.exist", role.getParentId());
            }
        }
    }
}
