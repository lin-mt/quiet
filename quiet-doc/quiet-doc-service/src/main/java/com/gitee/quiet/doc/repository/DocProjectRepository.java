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

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.jpa.repository.QuietRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Project Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocProjectRepository extends QuietRepository<DocProject> {

    /**
     * 根据用户ID查询用户负责的项目和可访问的项目
     *
     * @param userId 用户ID
     * @return 可访问的所有项目
     */
    @Query(value = "select * from doc_project where principal = :userId or find_in_set(visitor_id, :userId)", nativeQuery = true)
    List<DocProject> listAllByUserId(@Param("userId") Long userId);
}
