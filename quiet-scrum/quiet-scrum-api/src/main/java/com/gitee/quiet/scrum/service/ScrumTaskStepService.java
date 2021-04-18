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

import com.gitee.quiet.scrum.entity.ScrumTaskStep;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 任务步骤service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTaskStepService {
    
    /**
     * 新增任务步骤信息
     *
     * @param save 新增任务步骤
     * @return 新增任务步骤后的信息
     */
    ScrumTaskStep save(@NotNull ScrumTaskStep save);
    
    /**
     * 更新任务步骤信息
     *
     * @param update 更新的任务步骤信息
     * @return 更新后的任务步骤信息
     */
    ScrumTaskStep update(@NotNull ScrumTaskStep update);
    
    /**
     * 根据模板ID查询任务步骤信息
     *
     * @param templateId 模板ID
     * @return 模板下的任务步骤
     */
    List<ScrumTaskStep> findAllByTemplateId(@NotNull Long templateId);
    
    /**
     * 根据模板ID集合查询所有任务步骤信息
     *
     * @param templateIds 模板ID集合
     * @return 模板下的所有任务步骤
     */
    Map<Long, List<ScrumTaskStep>> findAllByTemplateIds(@NotNull @NotEmpty Set<Long> templateIds);
    
    /**
     * 删除任务步骤信息
     *
     * @param id 任务步骤ID
     */
    void deleteById(@NotNull Long id);
    
    /**
     * 批量更新任务步骤信息
     *
     * @param taskSteps 任务步骤信息
     */
    void updateBatch(List<ScrumTaskStep> taskSteps);
}
