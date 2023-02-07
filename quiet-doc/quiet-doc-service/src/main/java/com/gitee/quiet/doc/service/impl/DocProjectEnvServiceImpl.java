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

import com.gitee.quiet.doc.entity.DocProjectEnv;
import com.gitee.quiet.doc.repository.DocProjectEnvRepository;
import com.gitee.quiet.doc.service.DocProjectEnvService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目环境服务实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectEnvServiceImpl implements DocProjectEnvService {

  private final DocProjectEnvRepository repository;

  @Override
  public List<DocProjectEnv> listByProjectId(Long projectId) {
    return repository.findAllByProjectId(projectId);
  }

  @Override
  public DocProjectEnv saveOrUpdate(DocProjectEnv entity) {
    DocProjectEnv exist =
        repository.findByProjectIdAndName(entity.getProjectId(), entity.getName());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException(
          "projectEnv.name.exist", entity.getProjectId().toString(), entity.getName());
    }
    return repository.save(entity);
  }

  @Override
  public void deleteById(Long id) {
    repository.findById(id).orElseThrow(() -> new ServiceException("projectEnv.id.not.exist", id));
    repository.deleteById(id);
  }

  @Override
  public DocProjectEnv getById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ServiceException("projectEnv.id.not.exist", id));
  }
}
