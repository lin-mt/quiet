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

import com.gitee.quiet.doc.entity.DocApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 接口文档Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiRepository extends JpaRepository<DocApi, Long> {
    
    /**
     * 根据项目ID查询所有接口文档
     *
     * @param projectId 项目ID
     * @return 接口文档信息
     */
    List<DocApi> findAllByProjectId(Long projectId);
    
    /**
     * 根据分组ID查询在该分组的所有接口文档
     *
     * @param groupId 分组ID
     * @return 接口文档信息
     */
    @Query(nativeQuery = true, value = "select id, api_group_id from doc_api where find_in_set(:groupId, api_group_id)")
    List<DocApi> findAllByGroupId(@Param("groupId") Long groupId);
}
