/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.entity.DocProjectGroup;
import com.gitee.quiet.doc.repository.DocProjectGroupRepository;
import com.gitee.quiet.doc.service.DocProjectGroupService;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.gitee.quiet.doc.entity.QDocProjectGroup.docProjectGroup;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectGroupServiceImpl implements DocProjectGroupService {

  private final DocProjectGroupRepository repository;

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public DocProjectGroup saveOrUpdate(DocProjectGroup entity) {
    DocProjectGroup projectGroup = repository.findByName(entity.getName());
    if (projectGroup != null && !projectGroup.getId().equals(entity.getId())) {
      throw new ServiceException("projectGroup.name.exist", entity.getName());
    }
    return repository.saveAndFlush(entity);
  }

  @Override
  public List<DocProjectGroup> listAll(String name, Set<Long> groupIds) {
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .notBlankContains(name, docProjectGroup.name)
            .notEmptyIn(groupIds, docProjectGroup.id)
            .getPredicate();
    return jpaQueryFactory.selectFrom(docProjectGroup).where(where).fetch();
  }
}
