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

import com.gitee.quiet.scrum.entity.ScrumVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    
    /**
     * 根据项目ID和项目名称查询版本信息
     *
     * @param projectId 项目ID
     * @param name      项目名称
     * @return 版本信息
     */
    ScrumVersion findByProjectIdAndName(Long projectId, String name);
    
    /**
     * 根据父版本ID统计该父版本下有多少子版本
     *
     * @param parentId 父版本ID
     * @return 子版本数量
     */
    long countByParentId(Long parentId);
    
    /**
     * 查询下一个版本的信息
     *
     * @param planStartDate 当前版本的计划开始时间
     * @return 下一个版本的版本信息
     */
    ScrumVersion findFirstByPlanStartDateAfterOrderByPlanEndDateAsc(LocalDate planStartDate);
}
