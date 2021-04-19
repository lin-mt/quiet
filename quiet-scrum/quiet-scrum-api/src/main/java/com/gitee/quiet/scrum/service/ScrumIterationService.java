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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumIteration;

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
}