/*
 * Copyright 2021 lin-mt@outlook.com
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
import com.gitee.quiet.common.service.util.EntityUtils;
import com.gitee.quiet.system.entity.QuietDepartment;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietDepartmentRepository;
import com.gitee.quiet.system.service.QuietDepartmentService;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.gitee.quiet.system.entity.QQuietDepartment.quietDepartment;
import static com.gitee.quiet.system.entity.QQuietDepartmentUser.quietDepartmentUser;
import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 部门Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDepartmentServiceImpl implements QuietDepartmentService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietDepartmentRepository departmentRepository;
    
    private final QuietDepartmentUserService departmentUserService;
    
    public QuietDepartmentServiceImpl(JPAQueryFactory jpaQueryFactory, QuietDepartmentRepository departmentRepository,
            QuietDepartmentUserService departmentUserService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.departmentRepository = departmentRepository;
        this.departmentUserService = departmentUserService;
    }
    
    @Override
    public QueryResults<QuietDepartment> page(QuietDepartment params, @NotNull Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietDepartment, page);
    }
    
    @Override
    public QuietDepartment saveOrUpdate(@NotNull QuietDepartment department) {
        if (department.getParentId() != null) {
            if (!departmentRepository.existsById(department.getParentId())) {
                throw new ServiceException("department.not.exit.id", department.getParentId());
            }
            QuietDepartment exist = departmentRepository.getByDepartmentName(department.getDepartmentName());
            if (exist != null && !exist.getId().equals(department.getId())) {
                throw new ServiceException("department.departmentName.exist", department.getDepartmentName());
            }
        }
        return departmentRepository.saveAndFlush(department);
    }
    
    @Override
    public void deleteById(@NotNull Long deleteId) {
        if (CollectionUtils.isNotEmpty(departmentRepository.findAllByParentId(deleteId))) {
            throw new ServiceException("department.has.children.can.not.deleted");
        }
        if (CollectionUtils.isNotEmpty(departmentUserService.listAllByDepartmentId(deleteId))) {
            throw new ServiceException("department.has.member.can.not.deleted");
        }
        departmentRepository.deleteById(deleteId);
    }
    
    @Override
    public List<QuietDepartment> tree() {
        return EntityUtils.buildTreeData(departmentRepository.findAll());
    }
    
    @Override
    public QueryResults<QuietUser> pageUser(@NotNull Long departmentId, QuietUser params, @NotNull Pageable page) {
        BooleanBuilder builder = SelectBuilder.booleanBuilder(params).getPredicate();
        builder.and(quietDepartmentUser.departmentId.eq(departmentId));
        return jpaQueryFactory.selectFrom(quietUser).leftJoin(quietDepartmentUser)
                .on(quietUser.id.eq(quietDepartmentUser.userId)).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
}
