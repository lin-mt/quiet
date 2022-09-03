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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDictType;
import com.gitee.quiet.system.repository.QuietDictTypeRepository;
import com.gitee.quiet.system.service.QuietDictTypeService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gitee.quiet.system.entity.QQuietDictType.quietDictType;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDictTypeServiceImpl implements QuietDictTypeService {

  private final QuietDictTypeRepository repository;

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<QuietDictType> page(QuietDictType entity, Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(entity).getPredicate();
    return repository.findAll(predicate, page);
  }

  @Override
  public QuietDictType saveOrUpdate(QuietDictType entity) {
    QuietDictType exist =
        repository.findByServiceIdAndName(entity.getServiceId(), entity.getName());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException(
          "dictType.serviceId.name.exist", entity.getServiceId(), entity.getName());
    }
    return repository.saveAndFlush(entity);
  }

  @Override
  public QuietDictType getById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ServiceException("dictType.id.not.exist", id));
  }

  @Override
  public List<QuietDictType> listByEnabledAndName(boolean enabled, String name) {
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .notNullEq(enabled, quietDictType.enabled)
            .notBlankContains(name, quietDictType.name)
            .getPredicate();
    return jpaQueryFactory.selectFrom(quietDictType).where(where).fetch();
  }
}
