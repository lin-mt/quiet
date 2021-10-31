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
 *
 */

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocProjectEnvironment;

import java.util.List;

/**
 * 项目环境 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocProjectEnvironmentService {
    
    /**
     * 根据项目ID查询项目所有的环境配置信息
     *
     * @param projectId 项目ID
     * @return 项目所有的环境配置信息
     */
    List<DocProjectEnvironment> listByProjectId(Long projectId);
    
    /**
     * 创建项目环境
     *
     * @param save 环境信息
     * @return 创建的环境信息
     */
    DocProjectEnvironment save(DocProjectEnvironment save);
    
    /**
     * 更新项目环境
     *
     * @param update 环境信息
     * @return 更新的环境信息
     */
    DocProjectEnvironment update(DocProjectEnvironment update);
    
    /**
     * 根据ID删除数据
     *
     * @param id 要删除的数据的id
     */
    void deleteById(Long id);
}
