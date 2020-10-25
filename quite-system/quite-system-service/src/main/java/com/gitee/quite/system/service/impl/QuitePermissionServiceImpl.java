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

import com.gitee.quite.common.service.util.Wus;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.repository.QuitePermissionRepository;
import com.gitee.quite.system.service.QuitePermissionService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    
    public QuitePermissionServiceImpl(QuitePermissionRepository permissionRepository, JPAQueryFactory jpaQueryFactory) {
        this.permissionRepository = permissionRepository;
        this.jpaQueryFactory = jpaQueryFactory;
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
        Wus.NotBlankContains(params.getPreFilterValue(), quitePermission.preFilterValue, builder);
        Wus.NotBlankContains(params.getPreFilterFilterTarget(), quitePermission.preFilterFilterTarget, builder);
        Wus.NotBlankContains(params.getPreAuthorizeValue(), quitePermission.preAuthorizeValue, builder);
        Wus.NotBlankContains(params.getPostFilterValue(), quitePermission.postFilterValue, builder);
        Wus.NotBlankContains(params.getPostAuthorizeValue(), quitePermission.postAuthorizeValue, builder);
        return jpaQueryFactory.selectFrom(quitePermission).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
    
}
