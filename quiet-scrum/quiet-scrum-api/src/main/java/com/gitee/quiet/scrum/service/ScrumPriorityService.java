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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumPriority;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 优先级信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumPriorityService {
    
    /**
     * 保存优先级选项信息
     *
     * @param save 新增的优先级信息
     * @return 新增后的优先级信息
     */
    ScrumPriority save(ScrumPriority save);
    
    /**
     * 更新优先级信息
     *
     * @param update 更新的优先级信息
     * @return 更新后的优先级信息
     */
    ScrumPriority update(ScrumPriority update);
    
    /**
     * 根据id删除优先级信息
     *
     * @param id 要删除的优先级ID
     */
    void deleteById(Long id);
    
    /**
     * 根据模板ID删除优先级信息
     *
     * @param templateId 模板ID
     */
    void deleteByTemplateId(Long templateId);
    
    /**
     * 根据模板ID集合查询模板的所有优先级配置
     *
     * @param templateIds 模板ID集合
     * @return 模板下的所有优先级信息
     */
    Map<Long, List<ScrumPriority>> findAllByTemplateIds(Set<Long> templateIds);
}
