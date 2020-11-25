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

import com.gitee.quite.system.entity.QuiteUser;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteUserService extends UserDetailsService {
    
    /**
     * 新增用户.
     *
     * @param quiteUser 用户信息
     * @return true：保存成功 false：保存失败
     */
    QuiteUser save(QuiteUser quiteUser);
    
    /**
     * 删除用户.
     *
     * @param deleteId 要删除的用户的ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
    
    /**
     * 更新用户信息.
     *
     * @param user 要更新的用户信息
     * @return 更新后的用户信息
     */
    QuiteUser update(QuiteUser user);
    
    /**
     * 根据实体数据查询.
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuiteUser> page(QuiteUser params, Pageable page);
    
    /**
     * 判断该用户 ID 是否存在
     *
     * @param userId 用户ID
     * @return true：存在，false：不存在
     */
    boolean existsById(Long userId);
}
