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
import com.gitee.quiet.jpa.repository.QuietRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Api 分组Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiGroupRepository extends QuietRepository<DocApiGroup> {
    
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
    
    /**
     * 根据项目Id和项目名称模糊查询指定数据的分组信息
     *
     * @param projectId 项目ID
     * @param name      项目名称
     * @param limit     条数
     * @return 分组信息
     */
    @Query(nativeQuery = true, value = "select * from doc_api_group "
            + "where project_id = :projectId and group_name like concat('%', :name, '%') limit :limit")
    List<DocApiGroup> findAllByProjectIdAndName(@Param("projectId") Long projectId, @Param("name") String name,
            @Param("limit") long limit);
}