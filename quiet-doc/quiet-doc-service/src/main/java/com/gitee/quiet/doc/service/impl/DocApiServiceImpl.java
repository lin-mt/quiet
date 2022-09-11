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

import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.repository.DocApiRepository;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.gitee.quiet.doc.entity.QDocApi.docApi;

/**
 * Api Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocApiServiceImpl implements DocApiService {

  private final DocApiRepository repository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<DocApi> listAllByProjectId(Long projectId) {
    return repository.findAllByProjectId(projectId);
  }

  @Override
  public void removeGroup(Long groupId) {
    List<DocApi> apis = repository.findAllByApiGroupId(groupId);
    if (CollectionUtils.isNotEmpty(apis)) {
      apis.forEach(api -> api.setApiGroupId(null));
      repository.saveAll(apis);
    }
  }

  @Override
  public DocApi save(DocApi save) {
    checkInfo(save);
    return repository.save(save);
  }

  @Override
  public DocApi update(DocApi update) {
    checkInfo(update);
    return repository.saveAndFlush(update);
  }

  private void checkInfo(DocApi api) {
    DocApi exist = repository.findByProjectIdAndName(api.getProjectId(), api.getName());
    if (exist != null && !exist.getId().equals(api.getId())) {
      throw new ServiceException("api.name.exist", api.getProjectId(), api.getName());
    }
  }

  @Override
  public void deleteById(Long id) {
    repository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
    repository.deleteById(id);
  }

  @Override
  public DocApi getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
  }

  @Override
  public void checkId(Long id) {
    if (!repository.existsById(id)) {
      throw new ServiceException("api.id.notExist");
    }
  }

  @Override
  public void saveAll(Collection<DocApi> docApis) {
    if (CollectionUtils.isEmpty(docApis)) {
      return;
    }
    repository.saveAll(docApis);
  }

  @Override
  public List<DocApi> listByProjectIdAndName(Long projectId, String name, Long limit) {
    if (Objects.isNull(projectId)) {
      return Lists.newArrayList();
    }
    BooleanBuilder where =
            SelectBooleanBuilder.booleanBuilder()
                    .and(docApi.projectId.eq(projectId))
                    .notBlankContains(name, docApi.name)
                    .getPredicate();
    JPAQuery<DocApi> query = jpaQueryFactory.selectFrom(docApi).where(where);
    if (limit != null && limit > 0) {
      query.limit(limit);
    }
    return query.fetch();
  }

  @Override
  public Page<DocApi> page(DocApi entity, Pageable page) {
    BooleanBuilder where = SelectBooleanBuilder.booleanBuilder(entity).getPredicate();
    return repository.findAll(where, page);
  }
}
