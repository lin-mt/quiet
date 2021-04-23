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

import com.gitee.quiet.scrum.entity.ScrumPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 优先级repository
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumPriorityRepository extends JpaRepository<ScrumPriority, Long> {
    
    /**
     * 根据模板ID和优先级名称查询优先级信息
     *
     * @param templateId 模板ID
     * @param name       优先级名称
     * @return 优先级信息
     */
    ScrumPriority findByTemplateIdAndName(Long templateId, String name);
    
    /**
     * 根据模板ID查询优先级信息
     *
     * @param templateId 模板ID
     * @return 优先级信息
     */
    List<ScrumPriority> findAllByTemplateId(Long templateId);
    
    /**
     * 根据模板ID集合批量查询所有的优先级配置
     *
     * @param templateIds 模板ID集合
     * @return 所有优先级配置信息
     */
    List<ScrumPriority> findAllByTemplateIdIn(Set<Long> templateIds);
}
