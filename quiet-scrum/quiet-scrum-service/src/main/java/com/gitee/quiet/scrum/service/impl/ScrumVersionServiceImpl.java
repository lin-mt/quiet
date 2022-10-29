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

import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.repository.ScrumVersionRepository;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版本信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumVersionServiceImpl implements ScrumVersionService {

  private final ScrumVersionRepository versionRepository;

  @Override
  public List<ScrumVersion> list(Long projectId) {
    return versionRepository.findAllByProjectId(projectId);
  }

  @Override
  public ScrumVersion saveOrUpdate(ScrumVersion entity) {
    ScrumVersion exist =
            versionRepository.findByProjectIdAndName(entity.getProjectId(), entity.getName());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException(
              "version.project.name.exist", entity.getProjectId(), entity.getName());
    }
    if (entity.getParentId() != null && !versionRepository.existsById(entity.getParentId())) {
      throw new ServiceException("version.id.not.exist", entity.getParentId());
    }
    return versionRepository.save(entity);
  }

  @Override
  public ScrumVersion getById(Long id) {
    return versionRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("version.id.not.exist"));
  }

  @Override
  public List<ScrumVersion> listAllChildren(Long id) {
    return versionRepository.findAllByParentId(id);
  }

}
