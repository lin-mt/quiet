/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目版本repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumVersionRepository extends JpaRepository<ScrumVersion, Long> {
    
    /**
     * 根据项目id删除该项目下的所有版本信息
     *
     * @param projectId 项目ID
     */
    void deleteByProjectId(Long projectId);
    
    /**
     * 根据项目ID查询项目版本信息
     *
     * @param projectId 项目ID
     * @return 项目所有版本信息
     */
    List<ScrumVersion> findAllByProjectId(Long projectId);
}
