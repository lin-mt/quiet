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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocProjectEnvironment;
import com.gitee.quiet.jpa.repository.QuietRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 项目环境数据仓库.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocProjectEnvironmentRepository extends QuietRepository<DocProjectEnvironment> {

    /**
     * 根据项目ID查询所有数据
     *
     * @param projectId 项目ID
     * @return 项目ID的所有环境配置
     */
    List<DocProjectEnvironment> findAllByProjectId(Long projectId);

    /**
     * 根据项目ID和项目名称查询信息
     *
     * @param projectId 项目ID
     * @param name      项目名称
     * @return 项目信息
     */
    DocProjectEnvironment findByProjectIdAndName(Long projectId, String name);
}
