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

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.vo.MyScrumProject;
import com.gitee.quiet.scrum.vo.ScrumProjectDetail;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 项目Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectService {
    
    /**
     * 获取用户的所有项目信息
     *
     * @param userId 用户ID
     * @return 项目信息
     */
    MyScrumProject allProjectByUserId(@NotNull Long userId);
    
    /**
     * 新增项目
     *
     * @param save 新增的项目信息
     * @return 新增后的项目信息
     */
    ScrumProject save(@NotNull ScrumProject save);
    
    /**
     * 根据项目ID查询项目信息
     *
     * @param ids 项目ID集合
     * @return 项目信息
     */
    List<ScrumProject> findAllByIds(Set<Long> ids);
    
    /**
     * 更新项目信息
     *
     * @param update 更新的项目信息
     * @return 更新后的项目信息
     */
    ScrumProject update(@NotNull ScrumProject update);
    
    /**
     * 根据项目ID删除项目信息
     *
     * @param id 要删除的项目的ID
     */
    void deleteById(@NotNull Long id);
    
    /**
     * 统计多少项目用了指定的模板
     *
     * @param templateId 统计的模板ID
     * @return 使用了该模板的项目数量
     */
    long countByTemplateId(@NotNull Long templateId);
    
    /**
     * 获取项目的详细信息
     *
     * @param id 项目ID
     * @return 项目详细信息
     */
    ScrumProjectDetail getDetail(@NotNull Long id);
    
    /**
     * 根据项目ID获取项目信息
     *
     * @param id 项目ID
     * @return 项目信息
     */
    ScrumProject projectInfo(Long id);
}
