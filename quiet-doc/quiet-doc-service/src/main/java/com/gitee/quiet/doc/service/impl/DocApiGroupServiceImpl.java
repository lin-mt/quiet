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

import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.repository.DocApiGroupRepository;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.service.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 文档分组Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiGroupServiceImpl implements DocApiGroupService {

  private final DocApiGroupRepository apiGroupRepository;

  private final DocApiService apiService;

  public DocApiGroupServiceImpl(
      DocApiGroupRepository apiGroupRepository, DocApiService apiService) {
    this.apiGroupRepository = apiGroupRepository;
    this.apiService = apiService;
  }

  @Override
  public DocApiGroup save(DocApiGroup save) {
    checkInfo(save);
    return apiGroupRepository.save(save);
  }

  @Override
  public DocApiGroup update(DocApiGroup update) {
    checkInfo(update);
    apiGroupRepository
        .findById(update.getId())
        .orElseThrow(() -> new ServiceException("api.group.id.notExist", update.getId()));
    return apiGroupRepository.saveAndFlush(update);
  }

  private void checkInfo(DocApiGroup apiGroup) {
    DocApiGroup exist =
        apiGroupRepository.findByProjectIdAndName(apiGroup.getProjectId(), apiGroup.getName());
    if (exist != null && !exist.getId().equals(apiGroup.getId())) {
      throw new ServiceException(
          "api.group.name.exist", apiGroup.getProjectId(), apiGroup.getName());
    }
  }

  @Override
  public void deleteById(Long id) {
    apiGroupRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("api.group.id.notExist", id));
    apiService.removeGroup(id);
    apiGroupRepository.deleteById(id);
  }

  @Override
  public List<DocApiGroup> listByProjectId(Long projectId) {
    return apiGroupRepository.findAllByProjectId(projectId);
  }

  @Override
  public List<DocApiGroup> listByProjectIdAndName(Long projectId, String name, long limit) {
    if (Objects.isNull(projectId) || StringUtils.isBlank(name) || limit <= 0) {
      return Lists.newArrayList();
    }
    return apiGroupRepository.findAllByProjectIdAndName(projectId, name, limit);
  }

  @Override
  public DocApiGroup findById(Long id) {
    return apiGroupRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("api.group.id.notExist", id));
  }
}
