/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

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
   * @param routeId 网关路由ID
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
