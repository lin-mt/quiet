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
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDict;
import com.gitee.quiet.system.repository.QuietDictRepository;
import com.gitee.quiet.system.service.QuietDictService;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDictServiceImpl implements QuietDictService {

  private final QuietDictRepository repository;

  @Override
  public List<QuietDict> listByTypeId(Long typeId) {
    if (Objects.isNull(typeId)) {
      return List.of();
    }
    return repository.findAllByTypeId(typeId);
  }

  @Override
  public Page<QuietDict> page(QuietDict entity, Pageable page) {
    BooleanBuilder predicate = SelectBooleanBuilder.booleanBuilder(entity).getPredicate();
    return repository.findAll(predicate, page);
  }

  @Override
  public void deleteById(Long id) {
    QuietDict dict = getById(id);
    List<QuietDict> children =
        repository.findByTypeIdAndKeyIsStartingWith(dict.getTypeId(), dict.getKey()).stream()
            .filter(quietDict -> !quietDict.getId().equals(id))
            .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(children)) {
      throw new ServiceException(
          "dict.has.children.canNot.delete", dict.getTypeId(), dict.getKey());
    }
    repository.deleteById(id);
  }

  @Override
  public QuietDict getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new ServiceException("dict.id.not.exist", id));
  }

  @Override
  public QuietDict getByTypeIdAndKey(Long typeId, String key) {
    return repository.findByTypeIdAndKey(typeId, key);
  }
}
