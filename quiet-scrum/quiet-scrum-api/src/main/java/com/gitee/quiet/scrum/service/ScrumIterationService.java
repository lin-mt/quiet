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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumIteration;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 迭代信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumIterationService {
    
    /**
     * 根据版本ID集合批量删除迭代信息
     *
     * @param versionIds 要删除的迭代信息所属的版本ID集合
     */
    void deleteByVersionIds(Set<Long> versionIds);
    
    /**
     * 根据版本ID集合批量查询迭代信息
     *
     * @param versionIds 要查询的版本ID集合
     * @return 指定版本中所有的迭代信息
     */
    List<ScrumIteration> findAllByVersionIds(Set<Long> versionIds);
    
    /**
     * 新建迭代
     *
     * @param save 新建的迭代信息
     * @return 新建后的迭代信息
     */
    ScrumIteration save(ScrumIteration save);
    
    /**
     * 更新迭代信息
     *
     * @param update 更新的迭代信息
     * @return 更新后的迭代信息
     */
    ScrumIteration update(ScrumIteration update);
    
    /**
     * 根据ID删除迭代信息
     *
     * @param id 迭代ID
     */
    void deleteById(@NotNull Long id);
    
    /**
     * 根据版本ID统计处于该版本下有多少迭代数量
     *
     * @param versionId 版本ID
     * @return 处于该版本下的迭代数量
     */
    long countByVersionId(@NotNull Long versionId);
    
    /**
     * 校验是否存在该id的迭代，不存在则抛出异常
     *
     * @param id 迭代id
     */
    void checkIdExist(Long id);
}
