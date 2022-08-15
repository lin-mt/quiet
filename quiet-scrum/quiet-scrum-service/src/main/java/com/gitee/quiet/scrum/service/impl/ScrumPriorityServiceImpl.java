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

import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumPriorityRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.service.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 迭代信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumPriorityServiceImpl implements ScrumPriorityService {

  private final ScrumPriorityRepository priorityRepository;

  private final ScrumTemplateService templateService;

  private final ScrumDemandService demandService;

  public ScrumPriorityServiceImpl(
      ScrumPriorityRepository priorityRepository,
      @Lazy ScrumTemplateService templateService,
      ScrumDemandService demandService) {
    this.priorityRepository = priorityRepository;
    this.templateService = templateService;
    this.demandService = demandService;
  }

  @Override
  public ScrumPriority save(ScrumPriority save) {
    checkInfo(save);
    return priorityRepository.save(save);
  }

  @Override
  public ScrumPriority update(ScrumPriority update) {
    checkInfo(update);
    return priorityRepository.saveAndFlush(update);
  }

  @Override
  public void deleteById(Long id) {
    if (demandService.countByPriorityId(id) > 0) {
      throw new ServiceException("priority.hasDemand.can.not.delete");
    }
    ScrumPriority delete = priorityRepository.getById(id);
    priorityRepository.deleteById(id);
    if (priorityRepository.countByTemplateId(delete.getTemplateId()) == 0) {
      ScrumTemplate template = templateService.findById(delete.getTemplateId());
      template.setEnabled(false);
      templateService.update(template);
    }
  }

  @Override
  public void deleteByTemplateId(Long templateId) {
    List<ScrumPriority> priorities = priorityRepository.findAllByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(priorities)) {
      for (ScrumPriority priority : priorities) {
        deleteById(priority.getId());
      }
    }
  }

  @Override
  public Map<Long, List<ScrumPriority>> findAllByTemplateIds(Set<Long> templateIds) {
    return priorityRepository.findAllByTemplateIdIn(templateIds).stream()
        .collect(Collectors.groupingBy(ScrumPriority::getTemplateId));
  }

  @Override
  public List<ScrumPriority> updateBatch(List<ScrumPriority> priorities) {
    if (CollectionUtils.isNotEmpty(priorities)) {
      for (ScrumPriority priority : priorities) {
        checkInfo(priority);
      }
      return priorityRepository.saveAll(priorities);
    }
    return List.of();
  }

  @Override
  public List<ScrumPriority> findAllByTemplateId(Long templateId) {
    return priorityRepository.findAllByTemplateId(templateId);
  }

  @Override
  public List<ScrumPriority> findAllByIds(Set<Long> ids) {
    if (CollectionUtils.isNotEmpty(ids)) {
      return priorityRepository.findAllById(ids);
    }
    return List.of();
  }

  @Override
  public long countByTemplateId(Long templateId) {
    return priorityRepository.countByTemplateId(templateId);
  }

  private void checkInfo(ScrumPriority priority) {
    if (!templateService.existsById(priority.getTemplateId())) {
      throw new ServiceException("template.id.not.exist");
    }
    ScrumPriority exist =
        priorityRepository.findByTemplateIdAndName(priority.getTemplateId(), priority.getName());
    if (exist != null && !exist.getId().equals(priority.getId())) {
      throw new ServiceException(
          "priority.templateId.name.exist", priority.getTemplateId(), priority.getName());
    }
  }
}
