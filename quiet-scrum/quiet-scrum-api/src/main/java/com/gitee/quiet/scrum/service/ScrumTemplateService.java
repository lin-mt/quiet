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

import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.vo.AllTemplate;

import javax.validation.constraints.NotNull;

/**
 * 模板信息Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTemplateService {
    
    /**
     * 查询所有的模板信息
     *
     * @return 根据是否创建人创建的模板进行分组
     */
    AllTemplate allTemplates();
    
    /**
     * 新增模板
     *
     * @param save 新增的模板信息
     * @return 新增后的模板信息
     */
    ScrumTemplate save(ScrumTemplate save);
    
    /**
     * 更新模板
     *
     * @param update 更新的模板信息
     * @return 更新后的模板信息
     */
    ScrumTemplate update(ScrumTemplate update);
    
    /**
     * 根据模板ID删除模板
     *
     * @param id 要删除的模板的ID
     */
    void deleteById(@NotNull Long id);
}
