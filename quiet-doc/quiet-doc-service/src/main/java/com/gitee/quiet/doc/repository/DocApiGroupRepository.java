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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocApiGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Api 分组Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiGroupRepository extends JpaRepository<DocApiGroup, Long> {
    
    /**
     * 根据项目ID和分组名称查询分组信息
     *
     * @param projectId 项目ID
     * @param name      分组名称
     * @return 分组信息
     */
    DocApiGroup findByProjectIdAndName(Long projectId, String name);
    
    /**
     * 根据项目ID查询所有接口分组
     *
     * @param projectId 项目ID
     * @return 项目所有接口分组
     */
    List<DocApiGroup> findAllByProjectId(Long projectId);
}
