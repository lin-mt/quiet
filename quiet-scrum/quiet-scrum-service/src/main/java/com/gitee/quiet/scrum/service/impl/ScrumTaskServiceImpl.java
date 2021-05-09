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

import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.repository.ScrumTaskRepository;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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
    
    private final ScrumTaskRepository taskRepository;
    
    public ScrumTaskServiceImpl(ScrumTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
}
