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

import com.gitee.quiet.doc.entity.DocApiGroup;
import java.util.List;

/**
 * Api分组Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiGroupService {

    /**
     * 新建接口分组
     *
     * @param save 新建的接口分组
     * @return 新建的接口分组
     */
    DocApiGroup save(DocApiGroup save);

    /**
     * 更新接口分组
     *
     * @param update 更新的接口分组
     * @return 更新后的接口分组
     */
    DocApiGroup update(DocApiGroup update);

    /**
     * 根据接口分组ID删除信息
     *
     * @param id 要删除的接口分组ID
     */
    void deleteById(Long id);

    /**
     * 根据项目ID查询所有接口分组信息
     *
     * @param projectId 项目ID
     * @return 所有接口分组信息
     */
    List<DocApiGroup> listByProjectId(Long projectId);

    /**
     * 根据项目ID和接口名称查询接口分组信息
     *
     * @param projectId 项目ID
     * @param name      分组名称
     * @param limit     查询条数
     * @return 接口分组信息
     */
    List<DocApiGroup> listByProjectIdAndName(Long projectId, String name, long limit);

    /**
     * 根据ID查询分组信息
     *
     * @param id 接口分组ID
     * @return 接口分组信息
     */
    DocApiGroup findById(Long id);
}
