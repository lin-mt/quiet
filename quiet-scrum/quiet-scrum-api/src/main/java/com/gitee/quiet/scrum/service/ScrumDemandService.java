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

import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 需求service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumDemandService {
    
    /**
     * 根据迭代ID查询该迭代的所有需求
     *
     * @param iterationId 迭代ID
     * @return 迭代中的所有需求
     */
    List<ScrumDemand> findAllByIterationId(@NotNull Long iterationId);
    
    /**
     * 分页查询需求信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 需求信息
     */
    QueryResults<ScrumDemand> page(ScrumDemand params, Pageable page);
    
    /**
     * 创建需求
     *
     * @param save 新需求
     * @return 创建后的需求信息
     */
    ScrumDemand save(@NotNull ScrumDemand save);
    
    /**
     * 更新需求
     *
     * @param update 更新的需求信息
     * @return 更新后的需求信息
     */
    ScrumDemand update(@NotNull ScrumDemand update);
    
    /**
     * 根据项目信息删除项目下的需求信息
     *
     * @param projectId 要删除需求的项目ID
     */
    void deleteAllByProjectId(@NotNull Long projectId);
    
    /**
     * 根据优先级ID统计处于该优先级的需求数量
     *
     * @param priorityId 优先级ID
     * @return 处于该优先级的需求数量
     */
    long countByPriorityId(@NotNull Long priorityId);
    
    /**
     * 查询待规划的需求
     *
     * @param projectId 项目ID
     * @param offset    跳过的数量
     * @param limit     查询数量
     * @return 项目待规划的需求
     */
    List<ScrumDemand> listToBePlanned(Long projectId, Long offset, Long limit);
    
    /**
     * 根据迭代ID统计处于该迭代的需求数量
     *
     * @param iterationId 迭代ID
     * @return 处于该迭代的需求数量
     */
    long countByIterationId(@NotNull Long iterationId);
    
    /**
     * 查询迭代的需求
     *
     * @param iteration 迭代ID
     * @param offset    跳过的数量
     * @param limit     查询数量
     * @return 迭代的需求
     */
    List<ScrumDemand> scrollIteration(Long iteration, Long offset, Long limit);
    
    /**
     * 根据ID删除需求
     *
     * @param id 需求ID
     */
    void deleteById(Long id);
    
    /**
     * 校验是否需求ID是否存在
     *
     * @param id 需求ID
     */
    void checkIdExist(Long id);
}