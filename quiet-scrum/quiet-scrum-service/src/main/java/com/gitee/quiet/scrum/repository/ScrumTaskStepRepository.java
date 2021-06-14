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

import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 任务步骤repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTaskStepRepository extends JpaRepository<ScrumTaskStep, Long> {
    
    /**
     * 根据模板ID和步骤名称查询步骤信息
     *
     * @param templateId 模板ID
     * @param name       任务步骤名称
     * @return 任务步骤信息
     */
    ScrumTaskStep findByTemplateIdAndName(Long templateId, String name);
    
    /**
     * 根据模板ID查询所有任务步骤
     *
     * @param templateId 模板ID
     * @return 任务步骤信息
     */
    List<ScrumTaskStep> findAllByTemplateId(Long templateId);
    
    /**
     * 根据模板ID集合查询所有任务步骤
     *
     * @param templateIds 模板ID集合
     * @return 所有任务步骤信息
     */
    List<ScrumTaskStep> findAllByTemplateIdIn(Set<Long> templateIds);
    
    /**
     * 根据模板ID统计该模板下任务有多少步骤
     *
     * @param templateId 模板ID
     * @return 任务步骤数
     */
    long countByTemplateId(Long templateId);
}
