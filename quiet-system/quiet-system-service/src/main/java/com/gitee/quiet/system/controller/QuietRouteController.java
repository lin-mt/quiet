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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.convert.QuietRouteConvert;
import com.gitee.quiet.system.dto.QuietRouteDTO;
import com.gitee.quiet.system.entity.QuietRoute;
import com.gitee.quiet.system.service.QuietRouteService;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由信息Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@PreAuthorize(value = "hasRole('SystemAdmin')")
public class QuietRouteController {
    
    private final QuietRouteService routeService;
    
    private final QuietRouteConvert routeConvert;
    
    /**
     * 分页查询网关路由.
     *
     * @param dto 查询参数
     * @return 查询所有信息
     */
    @GetMapping("/page")
    public Result<QueryResults<QuietRoute>> page(QuietRouteDTO dto) {
        return Result.success(routeService.page(routeConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 新增网关路由.
     *
     * @param dto 新增的网关路由信息
     * @return 新增的网关路由信息
     */
    @PostMapping
    public Result<QuietRoute> save(@RequestBody @Validated(Create.class) QuietRouteDTO dto) {
        return Result.createSuccess(routeService.save(routeConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除网关路由.
     *
     * @param id 删除的网关路由ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        routeService.delete(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 更新网关路由.
     *
     * @param dto 更新的网关路由信息
     * @return 新增后的网关路由信息
     */
    @PutMapping
    public Result<QuietRoute> update(@RequestBody @Validated(Update.class) QuietRouteDTO dto) {
        return Result.updateSuccess(routeService.update(routeConvert.dtoToEntity(dto)));
    }
    
    /**
     * 发布路由配置.
     *
     * @param dto :environment 发布的环境
     * @return 发布结果
     */
    @PostMapping("/publishRoute")
    public Result<Object> publishRoute(@RequestBody QuietRouteDTO dto) {
        routeService.publishRoute(dto.getEnvironment(), 100L);
        return Result.success();
    }
    
    /**
     * 移除路由断言
     *
     * @param dto :id 路由信息ID :routePredicate 要移除的路由断言
     * @return 移除结果
     */
    @PostMapping("/removePredicate")
    public Result<QuietRoute> removePredicate(@RequestBody QuietRouteDTO dto) {
        return Result.success(routeService.removePredicate(dto.getId(), dto.getRoutePredicate()));
    }
    
    /**
     * 移除路由过滤器
     *
     * @param dto :id 路由信息ID :routePredicate 要移除的过滤器
     * @return 移除结果
     */
    @PostMapping("/removeFilter")
    public Result<QuietRoute> removeFilter(@RequestBody QuietRouteDTO dto) {
        return Result.success(routeService.removeFilter(dto.getId(), dto.getRouteFilter()));
    }
}
