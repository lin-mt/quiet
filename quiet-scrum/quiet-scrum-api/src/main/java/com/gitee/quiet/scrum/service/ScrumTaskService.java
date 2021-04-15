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
}
