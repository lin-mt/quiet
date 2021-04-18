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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 任务repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTaskRepository extends JpaRepository<ScrumTask, Long> {
    
    /**
     * 根据需求ID查询任务信息
     *
     * @param demandIds 需求ID集合
     * @return 所有任务信息
     */
    List<ScrumTask> findAllByDemandIdIn(Set<Long> demandIds);
    
    /**
     * 根据需求ID集合批量删除任务信息
     *
     * @param demandIds 要删除的任务所属的需求ID集合
     */
    void deleteAllByDemandIdIn(Set<Long> demandIds);
    
    /**
     * 根据步骤ID查询任务信息
     *
     * @param taskStepId 任务步骤ID
     * @return 处于该任务步骤的所有任务信息
     */
    List<ScrumTask> findAllByTaskStepId(Long taskStepId);
}
