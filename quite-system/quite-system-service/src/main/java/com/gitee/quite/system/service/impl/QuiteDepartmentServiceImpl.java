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
import com.gitee.quite.system.entity.QuiteDepartment;
import com.gitee.quite.system.repository.QuiteDepartmentRepository;
import com.gitee.quite.system.service.QuiteDepartmentService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quite.system.entity.QQuiteDepartment.quiteDepartment;

/**
 * 部门Srvice实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteDepartmentServiceImpl implements QuiteDepartmentService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuiteDepartmentRepository departmentRepository;
    
    public QuiteDepartmentServiceImpl(JPAQueryFactory jpaQueryFactory, QuiteDepartmentRepository departmentRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.departmentRepository = departmentRepository;
    }
    
    @Override
    public QueryResults<QuiteDepartment> page(QuiteDepartment params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Wus.NotNullEq(params.getId(), quiteDepartment.id, builder);
        Wus.NotNullEq(params.getParentId(), quiteDepartment.parentId, builder);
        Wus.NotBlankContains(params.getDepartmentName(), quiteDepartment.departmentName, builder);
        Wus.NotBlankContains(params.getRemark(), quiteDepartment.remark, builder);
        return jpaQueryFactory.selectFrom(quiteDepartment).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
    }
    
    @Override
    public QuiteDepartment saveOrUpdate(QuiteDepartment department) {
        if (department.getParentId() != null) {
            if (!departmentRepository.existsById(department.getParentId())) {
                throw new ServiceException("department.not.exit.id", department.getParentId());
            }
            QuiteDepartment exist = departmentRepository.getByDepartmentName(department.getDepartmentName());
            if (exist != null && !exist.getId().equals(department.getId())) {
                throw new ServiceException("department.departmentName.exist", department.getDepartmentName());
            }
        }
        return departmentRepository.saveAndFlush(department);
    }
    
    @Override
    public void delete(Long deleteId) {
        if (CollectionUtils.isNotEmpty(departmentRepository.findAllByParentId(deleteId))) {
            throw new ServiceException("department.has.children.can.not.deleted");
        }
        // TODO 删除前确认是否有人员在该部门下
        departmentRepository.deleteById(deleteId);
    }
}
