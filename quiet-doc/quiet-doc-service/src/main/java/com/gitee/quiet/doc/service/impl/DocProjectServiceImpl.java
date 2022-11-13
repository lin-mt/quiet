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

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static com.gitee.quiet.doc.entity.QDocProject.docProject;

/**
 * Project Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectServiceImpl implements DocProjectService {

  public static final String CACHE_NAME = "quiet:doc:project:info";

  private final DocProjectRepository projectRepository;

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  @CacheEvict(cacheNames = CACHE_NAME, key = "#entity.id", condition = "#entity.id != null")
  public DocProject saveOrUpdate(@NotNull DocProject entity) {
    Long groupId = entity.getGroupId();
    SelectBooleanBuilder builder =
        SelectBooleanBuilder.booleanBuilder().and(docProject.name.eq(entity.getName()));
    if (groupId == null || groupId <= 0) {
      builder.and(docProject.creator.eq(CurrentUserUtil.getId())).and(docProject.groupId.isNull());
    } else {
      builder.and(docProject.groupId.eq(groupId));
    }
    DocProject exist =
        jpaQueryFactory.selectFrom(docProject).where(builder.getPredicate()).fetchOne();
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException("project.projectGroup.projectName.exist", entity.getName());
    }
    return projectRepository.saveAndFlush(entity);
  }

  @Override
  @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
  public void delete(Long id) {
    projectRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("project.id.not.exist", id));
    projectRepository.deleteById(id);
  }

  @Override
  @Cacheable(cacheNames = CACHE_NAME, key = "#id", condition = "#id != null ", sync = true)
  public DocProject getById(Long id) {
    return projectRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("project.id.not.exist", id));
  }

  @Override
  public List<DocProject> listAllByGroupId(Long groupId) {
    if (groupId == null || groupId <= 0) {
      BooleanBuilder where =
          SelectBooleanBuilder.booleanBuilder()
              .and(docProject.creator.eq(CurrentUserUtil.getId()))
              .and(docProject.groupId.isNull())
              .getPredicate();
      return jpaQueryFactory.selectFrom(docProject).where(where).fetch();
    }
    return projectRepository.findAllByGroupId(groupId);
  }

  @Override
  public List<DocProject> list(Long groupId, String name, Set<Long> ids, Long limit) {
    BooleanBuilder where =
        SelectBuilder.booleanBuilder()
            .isIdEq(groupId, docProject.groupId)
            .notBlankContains(name, docProject.name)
            .notEmptyIn(ids, docProject.id)
            .with(
                builder -> {
                  if (groupId == null) {
                    builder
                        .and(docProject.groupId.isNull())
                        .and(docProject.creator.eq(CurrentUserUtil.getId()));
                  }
                })
            .getPredicate();
    JPAQuery<DocProject> query = jpaQueryFactory.selectFrom(docProject).where(where);
    if (limit != null) {
      query.limit(limit);
    }
    return query.fetch();
  }
}
