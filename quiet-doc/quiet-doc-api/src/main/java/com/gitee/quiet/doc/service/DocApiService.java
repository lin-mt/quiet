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
}
