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

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.system.entity.QuietRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 路由信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietRouteService {
    
    /**
     * 分页查询网关路由信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 网关路由信息
     */
    Page<QuietRoute> page(QuietRoute params, Pageable page);
    
    /**
     * 新增网关路由配置信息
     *
     * @param save 要新增的网关路由信息
     * @return 新增后的网关路由信息
     */
    QuietRoute save(QuietRoute save);
    
    /**
     * 删除网关路由信息
     *
     * @param id 要删除的网关路由的ID
     */
    void delete(Long id);
    
    /**
     * 更新网关路由信息
     *
     * @param update 要更新的网关路由信息
     * @return 更新后的网关路由信息
     */
    QuietRoute update(QuietRoute update);
    
    /**
     * 移除网关路由的授权范围
     *
     * @param id        要移除授权范围的网关路由的ID
     * @param predicate 操作的predicate
     */
    QuietRoute removePredicate(Long id, String predicate);
    
    /**
     * 移除网关路由的授权类型
     *
     * @param id     要操作路由信息的ID
     * @param filter 操作的过滤配置
     */
    QuietRoute removeFilter(Long id, String filter);
    
    /**
     * 发布路由配置到Redis
     *
     * @param environment 发布的环境
     * @param timeOut     存储在redis的过期时间，null 则不设置过期时间
     */
    void publishRoute(Dictionary<?> environment, Long timeOut);
}
