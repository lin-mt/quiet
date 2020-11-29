/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.system.service;

import com.gitee.quite.system.entity.QuiteTeam;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

/**
 * 团队 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteTeamService {
    
    /**
     * 分页查询团队信息
     *
     * @param params 查询条件
     * @param page   分页条件
     * @return 查询结果
     */
    QueryResults<QuiteTeam> page(QuiteTeam params, Pageable page);
    
    /**
     * 新增或更新团队信息
     *
     * @param team 团队信息
     * @return 新增或更新后的团队信息
     */
    QuiteTeam saveOrUpdate(QuiteTeam team);
    
    /**
     * 删除团队信息
     *
     * @param deleteId 要删除的团队 ID
     */
    void deleteTeam(Long deleteId);
}
