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

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * 迭代信息repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumIterationRepository extends QuietRepository<ScrumIteration> {
    
    /**
     * 根据版本ID集合批量删除迭代信息
     *
     * @param versionIds 要删除的迭代所属的版本ID集合
     */
    void deleteAllByVersionIdIn(Set<Long> versionIds);
    
    /**
     * 根据版本ID查询迭代信息
     *
     * @param versionIds 要查询的版本ID集合
     * @return 所有迭代信息
     */
    List<ScrumIteration> findAllByVersionIdIn(Set<Long> versionIds);
    
    /**
     * 根据版本ID和迭代名称查询迭代信息
     *
     * @param versionId 版本ID
     * @param name      迭代名称
     * @return 迭代信息
     */
    ScrumIteration findByVersionIdAndName(Long versionId, String name);
    
    /**
     * 根据版本ID统计处于该版本下有多少迭代数量
     *
     * @param versionId 版本ID
     * @return 处于该版本下的迭代数量
     */
    long countByVersionId(Long versionId);
    
    /**
     * 根据版本ID查询版本下一个迭代信息
     *
     * @param versionId     版本ID
     * @param planStartDate 当前迭代的计划开始时间
     * @return 下一个迭代迭代信息
     */
    ScrumIteration findByVersionIdAndPlanStartDateAfter(Long versionId, LocalDate planStartDate);
    
    /**
     * 根据版本ID查询第一个迭代信息
     *
     * @param versionId 版本ID
     * @return 指定版本的第一个迭代信息
     */
    ScrumIteration findFirstByVersionIdOrderByPlanStartDateAsc(Long versionId);
}
