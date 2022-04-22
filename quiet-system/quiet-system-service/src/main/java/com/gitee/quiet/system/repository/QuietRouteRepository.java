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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietRoute;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 路由信息Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietRouteRepository extends QuietRepository<QuietRoute> {

    /**
     * 根据网关路由ID和环境查询网关路由配置信息
     *
     * @param routeId     网关路由ID
     * @param environment 环境
     * @return 网关路由信息
     */
    QuietRoute findByRouteIdAndEnvironment(String routeId, Dictionary<?> environment);

    /**
     * 查询指定环境下的路由配置
     *
     * @param environment 环境
     * @return 路由信息
     */
    List<QuietRoute> findByEnvironment(Dictionary<?> environment);
}
