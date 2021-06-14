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

package com.gitee.quiet.system.service;

import com.gitee.quiet.common.service.enums.Operation;
import com.gitee.quiet.system.entity.QuietClient;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * 客户端Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings("deprecation")
public interface QuietClientService extends ClientDetailsService {
    
    /**
     * 分页查询客户端信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 客户端信息
     */
    QueryResults<QuietClient> page(QuietClient params, Pageable page);
    
    /**
     * 新增客户端信息
     *
     * @param save 要新增的客户端信息
     * @return 新增后的客户端信息
     */
    QuietClient save(QuietClient save);
    
    /**
     * 删除客户端信息
     *
     * @param clientId 要删除的客户端的ID
     */
    void deleteClient(Long clientId);
    
    /**
     * 更新客户端信息
     *
     * @param update 要更新的客户端信息
     * @return 更新后的客户端信息
     */
    QuietClient update(QuietClient update);
    
    /**
     * 移除客户端的授权范围
     *
     * @param id        客户端的ID
     * @param scope     操作的授权范围
     * @param operation 操作类型：添加、移除
     */
    QuietClient changeClientScope(Long id, String scope, Operation operation);
    
    /**
     * 移除客户端的授权类型
     *
     * @param id                  客户端的ID
     * @param authorizedGrantType 操作的授权类型
     * @param operation           操作类型：添加、移除
     */
    QuietClient changeClientAuthorizedGrantType(Long id, String authorizedGrantType, Operation operation);
}
