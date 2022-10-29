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

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.scrum.entity.ScrumProjectGroup;
import com.gitee.quiet.scrum.repository.ScrumProjectGroupRepository;
import com.gitee.quiet.scrum.service.ScrumProjectGroupService;
import com.gitee.quiet.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.gitee.quiet.scrum.entity.QScrumProjectGroup.scrumProjectGroup;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumProjectGroupServiceImpl implements ScrumProjectGroupService {

  private final ScrumProjectGroupRepository repository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public ScrumProjectGroup saveOrUpdate(ScrumProjectGroup entity) {
    ScrumProjectGroup exist = repository.findByName(entity.getName());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException("projectGroup.name.exist", entity.getName());
    }
    return repository.saveAndFlush(entity);
  }

  @Override
  public List<ScrumProjectGroup> listAll(String name, Set<Long> groupIds) {
    BooleanBuilder where =
            SelectBooleanBuilder.booleanBuilder()
                    .notBlankContains(name, scrumProjectGroup.name)
                    .notEmptyIn(groupIds, scrumProjectGroup.id)
                    .getPredicate();
    return jpaQueryFactory.selectFrom(scrumProjectGroup).where(where).fetch();
  }
}
