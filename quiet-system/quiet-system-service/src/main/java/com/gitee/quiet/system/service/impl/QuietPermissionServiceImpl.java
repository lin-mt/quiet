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
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.security.UrlPermission;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.repository.QuietPermissionRepository;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.system.service.QuietRoleService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.system.entity.QQuietPermission.quietPermission;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietPermissionServiceImpl implements QuietPermissionService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietPermissionRepository permissionRepository;
    
    private final QuietRoleService roleService;
    
    public QuietPermissionServiceImpl(JPAQueryFactory jpaQueryFactory, QuietPermissionRepository permissionRepository,
            @Lazy QuietRoleService roleService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }
    
    @Override
    public QuietPermission saveOrUpdate(@NotNull QuietPermission permission) {
        if (!roleService.existsById(permission.getRoleId())) {
            throw new ServiceException("role.id.not.exist", permission.getRoleId());
        }
        return permissionRepository.saveAndFlush(permission);
    }
    
    @Override
    public void delete(@NotNull Long deleteId) {
        permissionRepository.deleteById(deleteId);
    }
    
    @Override
    public QueryResults<QuietPermission> page(QuietPermission params, @NotNull Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietPermission, page);
    }
    
    @Override
    public List<QuietPermission> listByRoleId(@NotNull Long roleId) {
        return permissionRepository.findAllByRoleId(roleId);
    }
    
    @Override
    public List<UrlPermission> listUrlPermission(@NotNull String applicationName) {
        // TODO 使用缓存
        List<QuietPermission> permissions = permissionRepository.findAllByApplicationName(applicationName);
        List<UrlPermission> urlPermissions = new ArrayList<>(permissions.size());
        if (!permissions.isEmpty()) {
            Set<Long> roleIds = permissions.stream().map(QuietPermission::getRoleId).collect(Collectors.toSet());
            Map<Long, String> roleIdToRoleName = roleService.findAllByIds(roleIds).stream()
                    .collect(Collectors.toMap(QuietRole::getId, QuietRole::getRoleName));
            UrlPermission urlPermission;
            for (QuietPermission permission : permissions) {
                urlPermission = new UrlPermission();
                urlPermission.setUrlPattern(permission.getUrlPattern());
                urlPermission.setRequestMethod(permission.getRequestMethod());
                String roleName = roleIdToRoleName.get(permission.getRoleId());
                if (StringUtils.isBlank(roleName)) {
                    throw new ServiceException("permission.roleId.notExist");
                }
                urlPermission.setRoleName(roleName);
                urlPermissions.add(urlPermission);
            }
        }
        return urlPermissions;
    }
}
