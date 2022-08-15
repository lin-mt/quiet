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

import com.gitee.quiet.jpa.utils.EntityUtils;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDepartment;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietDepartmentRepository;
import com.gitee.quiet.system.service.QuietDepartmentService;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

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

  public QuietDepartmentServiceImpl(
      JPAQueryFactory jpaQueryFactory,
      QuietDepartmentRepository departmentRepository,
      QuietDepartmentUserService departmentUserService) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.departmentRepository = departmentRepository;
    this.departmentUserService = departmentUserService;
  }

  @Override
  public Page<QuietDepartment> page(QuietDepartment params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return departmentRepository.findAll(predicate, page);
  }

  @Override
  public QuietDepartment saveOrUpdate(@NotNull QuietDepartment department) {
    if (department.getParentId() != null) {
      if (!departmentRepository.existsById(department.getParentId())) {
        throw new ServiceException("department.not.exit.id", department.getParentId());
      }
      QuietDepartment exist =
          departmentRepository.getByDepartmentName(department.getDepartmentName());
      if (exist != null && !exist.getId().equals(department.getId())) {
        throw new ServiceException(
            "department.departmentName.exist", department.getDepartmentName());
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
  public QueryResults<QuietUser> pageUser(
      @NotNull Long departmentId, QuietUser params, @NotNull Pageable page) {
    BooleanBuilder builder = SelectBuilder.booleanBuilder(params).getPredicate();
    builder.and(quietDepartmentUser.departmentId.eq(departmentId));
    return jpaQueryFactory
        .selectFrom(quietUser)
        .leftJoin(quietDepartmentUser)
        .on(quietUser.id.eq(quietDepartmentUser.userId))
        .where(builder)
        .offset(page.getOffset())
        .limit(page.getPageSize())
        .fetchResults();
  }
}
