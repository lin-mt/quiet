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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumTask;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 任务信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTaskService {
    
    /**
     * 根据需求ID集合查询指定需求下的所有任务，并根据任务步骤进行分组
     *
     * @param demandIds 需求ID集合
     * @return 根据需求ID和任务步骤分组后的任务集合
     */
    Map<Long, Map<Long, List<ScrumTask>>> findAllTaskByDemandIds(Set<Long> demandIds);
    
    /**
     * 根据需求ID集合批量删除任务信息
     *
     * @param demandIds 要删除的任务所属的需求ID集合
     */
    void deleteAllByDemandIds(Set<Long> demandIds);
    
    /**
     * 根据任务步骤查询任务信息
     *
     * @param taskStepId 任务步骤ID
     * @return 处于该任务步骤下的所有任务
     */
    List<ScrumTask> findAllByTaskStepId(Long taskStepId);
    
    /**
     * 新建任务
     *
     * @param save 新建的任务信息
     * @return 新建后的任务信息
     */
    ScrumTask save(ScrumTask save);
    
    /**
     * 更新任务
     *
     * @param update 更新的任务信息
     * @return 更新后的任务信息
     */
    ScrumTask update(ScrumTask update);
    
    /**
     * 根据任务ID删除任务信息
     *
     * @param id 任务ID
     */
    void deleteById(Long id);
    
    /**
     * 过滤指定项目中指定的需求中未完成的需求
     *
     * @param projectId 项目ID
     * @param demandIds 要过滤的需求ID集合
     * @return 未完成的需求的ID集合
     */
    Set<Long> findUnfinishedDemandIds(Long projectId, Set<Long> demandIds);
}
