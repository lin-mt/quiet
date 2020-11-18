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
import com.gitee.quite.common.service.security.UrlPermission;
import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.repository.QuitePermissionRepository;
import com.gitee.quite.system.service.QuitePermissionService;
import com.gitee.quite.system.service.QuiteRoleService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quite.system.entity.QQuitePermission.quitePermission;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuitePermissionServiceImpl implements QuitePermissionService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuitePermissionRepository permissionRepository;
    
    private final QuiteRoleService roleService;
    
    public QuitePermissionServiceImpl(JPAQueryFactory jpaQueryFactory, QuitePermissionRepository permissionRepository,
            QuiteRoleService roleService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }
    
    @Override
    public QuitePermission saveOrUpdate(QuitePermission permission) {
        return permissionRepository.save(permission);
    }
    
    @Override
    public boolean delete(Long deleteId) {
        permissionRepository.deleteById(deleteId);
        return true;
    }
    
    @Override
    public QueryResults<QuitePermission> page(QuitePermission params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quitePermission.id, builder);
        Wus.NotBlankContains(params.getApplicationName(), quitePermission.applicationName, builder);
        Wus.NotBlankContains(params.getUrlPattern(), quitePermission.urlPattern, builder);
        return jpaQueryFactory.selectFrom(quitePermission).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
    
    @Override
    public List<UrlPermission> listUrlPermission(String applicationName) {
        List<QuitePermission> permissions = permissionRepository.findAllByApplicationName(applicationName);
        List<UrlPermission> urlPermissions = new ArrayList<>(permissions.size());
        if (!permissions.isEmpty()) {
            Set<Long> roleIds = permissions.stream().map(QuitePermission::getRoleId).collect(Collectors.toSet());
            Map<Long, String> roleIdToRoleName = roleService.findAllByIds(roleIds).stream()
                    .collect(Collectors.toMap(QuiteRole::getId, QuiteRole::getRoleName));
            UrlPermission urlPermission;
            for (QuitePermission permission : permissions) {
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
