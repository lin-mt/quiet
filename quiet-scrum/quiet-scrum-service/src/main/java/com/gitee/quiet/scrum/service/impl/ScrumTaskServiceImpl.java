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
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.repository.ScrumTaskRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 任务信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumTaskServiceImpl implements ScrumTaskService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final ScrumTaskRepository taskRepository;
    
    private final ScrumDemandService demandService;
    
    private final ScrumTaskStepService taskStepService;
    
    public ScrumTaskServiceImpl(JPAQueryFactory jpaQueryFactory, ScrumTaskRepository taskRepository,
            @Lazy ScrumDemandService demandService, @Lazy ScrumTaskStepService taskStepService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.taskRepository = taskRepository;
        this.demandService = demandService;
        this.taskStepService = taskStepService;
    }
    
    @Override
    public Map<Long, Map<Long, List<ScrumTask>>> findAllTaskByDemandIds(Set<Long> demandIds) {
        if (CollectionUtils.isEmpty(demandIds)) {
            return Map.of();
        }
        return taskRepository.findAllByDemandIdIn(demandIds).stream().collect(
                Collectors.groupingBy(ScrumTask::getDemandId, Collectors.groupingBy(ScrumTask::getTaskStepId)));
    }
    
    @Override
    public void deleteAllByDemandIds(Set<Long> demandIds) {
        if (CollectionUtils.isNotEmpty(demandIds)) {
            taskRepository.deleteAllByDemandIdIn(demandIds);
        }
    }
    
    @Override
    public List<ScrumTask> findAllByTaskStepId(Long taskStepId) {
        return taskRepository.findAllByTaskStepId(taskStepId);
    }
    
    @Override
    public ScrumTask save(ScrumTask save) {
        checkTaskInfo(save);
        return taskRepository.save(save);
    }
    
    @Override
    public ScrumTask update(ScrumTask update) {
        checkTaskInfo(update);
        return taskRepository.saveAndFlush(update);
    }
    
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            // TODO 校验是否存在后置任务
            throw new ServiceException("task.preTask.contains.canNotDelete", id);
        }
        taskRepository.deleteById(id);
    }
    
    private void checkTaskInfo(ScrumTask scrumTask) {
        // TODO 校验执行者和参与者信息
        demandService.checkIdExist(scrumTask.getDemandId());
        taskStepService.checkIdExist(scrumTask.getTaskStepId());
        ScrumTask exist = taskRepository.findByDemandIdAndTitle(scrumTask.getDemandId(), scrumTask.getTitle());
        if (exist != null && !exist.getId().equals(scrumTask.getId())) {
            throw new ServiceException("task.demandId.title.exist", scrumTask.getDemandId(), scrumTask.getTitle());
        }
        if (CollectionUtils.isNotEmpty(scrumTask.getPreTaskIds())) {
            Set<Long> existIds = taskRepository.findAllById(scrumTask.getPreTaskIds()).stream().map(ScrumTask::getId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(existIds) || !existIds.containsAll(scrumTask.getPreTaskIds())) {
                scrumTask.getPreTaskIds().removeAll(existIds);
                throw new ServiceException("task.ids.notExist", Arrays.toString(scrumTask.getPreTaskIds().toArray()));
            }
        }
    }
}
