/*
 * Copyright 2021. lin-mt@outlook.com
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
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumTaskStepRepository;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 任务步骤service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumTaskStepServiceImpl implements ScrumTaskStepService {
    
    private final ScrumTaskStepRepository taskStepRepository;
    
    private final ScrumTaskService taskService;
    
    private final ScrumTemplateService templateService;
    
    public ScrumTaskStepServiceImpl(ScrumTaskStepRepository taskStepRepository, ScrumTaskService taskService,
            @Lazy ScrumTemplateService templateService) {
        this.taskStepRepository = taskStepRepository;
        this.taskService = taskService;
        this.templateService = templateService;
    }
    
    @Override
    public ScrumTaskStep save(ScrumTaskStep save) {
        checkInfo(save);
        return taskStepRepository.save(save);
    }
    
    @Override
    public ScrumTaskStep update(ScrumTaskStep update) {
        checkInfo(update);
        return taskStepRepository.saveAndFlush(update);
    }
    
    @Override
    public List<ScrumTaskStep> findAllByTemplateId(Long templateId) {
        return taskStepRepository.findAllByTemplateId(templateId);
    }
    
    @Override
    public Map<Long, List<ScrumTaskStep>> findAllByTemplateIds(Set<Long> templateIds) {
        List<ScrumTaskStep> taskSteps = taskStepRepository.findAllByTemplateIdIn(templateIds);
        Map<Long, List<ScrumTaskStep>> templateIdToTaskSteps = new HashMap<>(templateIds.size());
        if (CollectionUtils.isNotEmpty(taskSteps)) {
            templateIdToTaskSteps = taskSteps.stream().collect(Collectors.groupingBy(ScrumTaskStep::getTemplateId));
        }
        return templateIdToTaskSteps;
    }
    
    @Override
    public void deleteById(Long id) {
        if (CollectionUtils.isNotEmpty(taskService.findAllByTaskStepId(id))) {
            throw new ServiceException("taskStep.hasTask.can.not.delete");
        }
        ScrumTaskStep delete = taskStepRepository.getOne(id);
        taskStepRepository.deleteById(id);
        if (taskStepRepository.countByTemplateId(delete.getTemplateId()) == 0) {
            ScrumTemplate template = templateService.findById(delete.getTemplateId());
            template.setEnabled(false);
            templateService.update(template);
        }
    }
    
    @Override
    public List<ScrumTaskStep> updateBatch(List<ScrumTaskStep> taskSteps) {
        if (CollectionUtils.isNotEmpty(taskSteps)) {
            for (ScrumTaskStep taskStep : taskSteps) {
                checkInfo(taskStep);
            }
            return taskStepRepository.saveAll(taskSteps);
        }
        return List.of();
    }
    
    @Override
    public void deleteByTemplateId(Long templateId) {
        List<ScrumTaskStep> taskSteps = taskStepRepository.findAllByTemplateId(templateId);
        if (CollectionUtils.isNotEmpty(taskSteps)) {
            for (ScrumTaskStep taskStep : taskSteps) {
                deleteById(taskStep.getId());
            }
        }
    }
    
    @Override
    public long countByTemplateId(Long templateId) {
        return taskStepRepository.countByTemplateId(templateId);
    }
    
    @Override
    public void checkIdExist(Long id) {
        if (!taskStepRepository.existsById(id)) {
            throw new ServiceException("taskStep.id.notExist", id);
        }
    }
    
    private void checkInfo(ScrumTaskStep taskStep) {
        if (!templateService.existsById(taskStep.getTemplateId())) {
            throw new ServiceException("template.id.not.exist");
        }
        ScrumTaskStep exist = taskStepRepository.findByTemplateIdAndName(taskStep.getTemplateId(), taskStep.getName());
        if (exist != null && !exist.getId().equals(taskStep.getId())) {
            throw new ServiceException("taskStep.templateId.name.exist", taskStep.getTemplateId(), taskStep.getName());
        }
    }
}
