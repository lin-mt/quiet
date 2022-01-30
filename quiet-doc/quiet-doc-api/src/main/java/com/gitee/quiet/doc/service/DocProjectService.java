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

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.vo.MyDocProject;

/**
 * Project Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocProjectService {
    
    /**
     * 根据用户ID查询用户的文档项目信息
     *
     * @param userId 用户ID
     * @return 可访问的项目信息
     */
    MyDocProject getProjectByUserId(Long userId);
    
    /**
     * 新建文档项目
     *
     * @param save 新增的文档信息
     * @return 新增的文档信息
     */
    DocProject save(DocProject save);
    
    /**
     * 更新文档项目
     *
     * @param update 更新的文档信息
     * @return 更新后的文档信息
     */
    DocProject update(DocProject update);
    
    /**
     * 根据ID删除文档项目
     *
     * @param id 文档项目ID
     */
    void delete(Long id);
    
    /**
     * 根据ID查询文档项目信息
     *
     * @param id 文档项目ID
     * @return 文档项目信息
     */
    DocProject getById(Long id);
}
