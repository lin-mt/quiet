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

import com.gitee.quiet.doc.entity.DocApi;

import java.util.List;

/**
 * Api Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiService {
    
    /**
     * 根据项目ID查询接口信息
     *
     * @param projectId 项目ID
     * @return 接口信息
     */
    List<DocApi> listAllByProjectId(Long projectId);
    
    /**
     * 移除接口中的分组
     *
     * @param groupId 移除的分组的ID
     */
    void removeGroup(Long groupId);
    
    /**
     * 新增接口信息
     *
     * @param save 新增的接口信息
     * @return 新增后的接口信息
     */
    DocApi save(DocApi save);
    
    /**
     * 更新接口信息
     *
     * @param update 更新的接口信息
     * @return 更新后的接口信息
     */
    DocApi update(DocApi update);
    
    /**
     * 根据接口ID删除接口信息
     *
     * @param id 接口ID
     */
    void deleteById(Long id);
}
