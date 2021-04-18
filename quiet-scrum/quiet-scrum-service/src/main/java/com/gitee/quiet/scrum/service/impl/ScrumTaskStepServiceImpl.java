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
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.repository.ScrumTaskStepRepository;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import org.apache.commons.collections4.CollectionUtils;
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
    
    public ScrumTaskStepServiceImpl(ScrumTaskStepRepository taskStepRepository, ScrumTaskService taskService) {
        this.taskStepRepository = taskStepRepository;
        this.taskService = taskService;
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
        taskStepRepository.deleteById(id);
    }
    
    @Override
    public void updateBatch(List<ScrumTaskStep> taskSteps) {
        if (CollectionUtils.isNotEmpty(taskSteps)) {
            taskStepRepository.saveAll(taskSteps);
        }
    }
    
    private void checkInfo(ScrumTaskStep taskStep) {
        ScrumTaskStep exist = taskStepRepository.findByTemplateIdAndName(taskStep.getTemplateId(), taskStep.getName());
        if (exist != null && !exist.getId().equals(taskStep.getId())) {
            throw new ServiceException("taskStep.templateId.name.exist", taskStep.getTemplateId(), taskStep.getName());
        }
    }
}
