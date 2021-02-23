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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumProjectRepository extends JpaRepository<ScrumProject, Long> {
    
    /**
     * 根据项目名称和负责经理查询项目信息
     *
     * @param name    项目名称
     * @param manager 项目经理ID
     * @return 项目信息
     */
    ScrumProject findByNameAndManager(String name, Long manager);
    
    /**
     * 根据项目经理ID查询负责的项目信息
     *
     * @param manager 项目经理ID
     * @return 项目信息
     */
    List<ScrumProject> findAllByManager(Long manager);
}
