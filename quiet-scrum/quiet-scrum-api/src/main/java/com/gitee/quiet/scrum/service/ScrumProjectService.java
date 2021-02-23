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

import com.gitee.quiet.scrum.MyScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProject;

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
    MyScrumProject allProjectByUserId(Long userId);
    
    /**
     * 新增项目
     *
     * @param save 新增的项目信息
     * @return 新增后的项目信息
     */
    ScrumProject save(ScrumProject save);
    
    /**
     * 根据项目ID查询项目信息
     *
     * @param ids 项目ID集合
     * @return 项目信息
     */
    List<ScrumProject> findAllByIds(Set<Long> ids);
}
