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

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.gitee.quiet.jpa.utils.EntityUtils;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDept;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietDeptRepository;
import com.gitee.quiet.system.service.QuietDeptService;
import com.gitee.quiet.system.service.QuietDeptUserService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.gitee.quiet.system.entity.QQuietDeptUser.quietDeptUser;
import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 部门Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDeptServiceImpl implements QuietDeptService {

  private final QuietDeptRepository deptRepository;

  private final QuietDeptUserService deptUserService;
  private final CriteriaBuilderFactory criteriaBuilderFactory;
  private final EntityManager entityManager;

  public QuietDeptServiceImpl(
      QuietDeptRepository deptRepository,
      QuietDeptUserService deptUserService,
      CriteriaBuilderFactory criteriaBuilderFactory,
      EntityManager entityManager) {
    this.deptRepository = deptRepository;
    this.deptUserService = deptUserService;
    this.criteriaBuilderFactory = criteriaBuilderFactory;
    this.entityManager = entityManager;
  }

  @Override
  public Page<QuietDept> page(QuietDept params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return deptRepository.findAll(predicate, page);
  }

  @Override
  public QuietDept saveOrUpdate(@NotNull QuietDept dept) {
    if (dept.getParentId() != null) {
      if (!deptRepository.existsById(dept.getParentId())) {
        throw new ServiceException("dept.not.exit.id", dept.getParentId());
      }
      QuietDept exist = deptRepository.getByDeptName(dept.getDeptName());
      if (exist != null && !exist.getId().equals(dept.getId())) {
        throw new ServiceException("dept.deptName.exist", dept.getDeptName());
      }
    }
    return deptRepository.saveAndFlush(dept);
  }

  @Override
  public void deleteById(@NotNull Long deleteId) {
    if (CollectionUtils.isNotEmpty(deptRepository.findAllByParentId(deleteId))) {
      throw new ServiceException("dept.has.children.can.not.deleted");
    }
    if (CollectionUtils.isNotEmpty(deptUserService.listAllByDeptId(deleteId))) {
      throw new ServiceException("dept.has.member.can.not.deleted");
    }
    deptRepository.deleteById(deleteId);
  }

  @Override
  public List<QuietDept> tree() {
    return EntityUtils.buildTreeData(deptRepository.findAll());
  }

  @Override
  public PagedList<QuietUser> pageUser(
      @NotNull Long deptId, QuietUser params, @NotNull Pageable page) {
    BooleanBuilder builder = SelectBuilder.booleanBuilder(params).getPredicate();
    builder.and(quietDeptUser.deptId.eq(deptId));
    return new BlazeJPAQuery<QuietUser>(entityManager, criteriaBuilderFactory)
        .select(quietUser)
        .from(quietUser)
        .leftJoin(quietDeptUser)
        .on(quietUser.id.eq(quietDeptUser.userId))
        .where(builder)
        .orderBy(quietUser.id.desc())
        .fetchPage((int) page.getOffset(), page.getPageSize());
  }
}
