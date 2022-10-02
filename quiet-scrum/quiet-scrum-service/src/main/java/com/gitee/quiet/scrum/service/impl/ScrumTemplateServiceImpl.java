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

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumTemplateRepository;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.gitee.quiet.scrum.entity.QScrumTemplate.scrumTemplate;

/**
 * 模板信息Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumTemplateServiceImpl implements ScrumTemplateService {

  private final ScrumTemplateRepository templateRepository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ScrumTemplate> findAllByIds(Set<Long> ids) {
    return templateRepository.findAllById(ids);
  }

  @Override
  public ScrumTemplate findById(Long id) {
    return templateRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("template.id.notExist", id));
  }

  @Override
  public List<ScrumTemplate> list(Long id, String name, Boolean enabled, Long limit) {
    if (limit == null) {
      limit = 15L;
    }
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .isIdEq(id, scrumTemplate.id)
            .notBlankContains(name, scrumTemplate.name)
            .notNullEq(enabled, scrumTemplate.enabled)
            .getPredicate();
    JPAQuery<ScrumTemplate> query = jpaQueryFactory.selectFrom(scrumTemplate).where(where);
    if (limit != 0) {
      query.limit(limit);
    }
    return query.fetch();
  }

  @Override
  public ScrumTemplate saveOrUpdate(ScrumTemplate entity) {
    ScrumTemplate exist = templateRepository.findByName(entity.getName());
    if (exist != null) {
      if (!exist.getId().equals(entity.getId())) {
        throw new ServiceException("template.name.exist", entity.getName());
      }
      entity.setEnabled(exist.getEnabled());
    } else {
      entity.setEnabled(false);
    }
    return templateRepository.saveAndFlush(entity);
  }
}
