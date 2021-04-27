/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.repository.ScrumPriorityRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
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
    
    public ScrumPriorityServiceImpl(ScrumPriorityRepository priorityRepository,
            @Lazy ScrumTemplateService templateService, ScrumDemandService demandService) {
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
        priorityRepository.deleteById(id);
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
    public void updateBatch(List<ScrumPriority> priorities) {
        if (CollectionUtils.isNotEmpty(priorities)) {
            for (ScrumPriority priority : priorities) {
                checkInfo(priority);
            }
            priorityRepository.saveAll(priorities);
        }
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
    
    private void checkInfo(ScrumPriority priority) {
        if (!templateService.existsById(priority.getTemplateId())) {
            throw new ServiceException("template.id.not.exist");
        }
        ScrumPriority exist = priorityRepository.findByTemplateIdAndName(priority.getTemplateId(), priority.getName());
        if (exist != null && !exist.getId().equals(priority.getId())) {
            throw new ServiceException("priority.templateId.name.exist", priority.getTemplateId(), priority.getName());
        }
    }
}
