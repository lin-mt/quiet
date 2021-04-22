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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 需求repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumDemandRepository extends JpaRepository<ScrumDemand, Long> {
    
    /**
     * 根据迭代ID查询该迭代的所有需求
     *
     * @param iterationId 迭代ID
     * @return 迭代中的所有需求
     */
    List<ScrumDemand> findAllByIterationId(Long iterationId);
    
    /**
     * 根据项目ID和需求标题查询需求信息
     *
     * @param projectId 项目ID
     * @param title     需求标题
     * @return 需求信息
     */
    ScrumDemand findByProjectIdAndTitle(Long projectId, String title);
    
    /**
     * 根据项目ID批量查询需求信息
     *
     * @param projectId 项目ID
     * @return 项目的所有需求信息
     */
    List<ScrumDemand> findAllByProjectId(Long projectId);
    
    /**
     * 根据优先级ID统计处于该优先级的需求有多少
     *
     * @param priorityId 优先级ID
     * @return 处于该优先级的需求数量
     */
    long countByPriorityId(Long priorityId);
}
