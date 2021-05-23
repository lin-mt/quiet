/*
 * Copyright $.today.year lin-mt@outlook.com
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

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.enums.Operation;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietRoute;
import com.gitee.quiet.system.params.QuietRouteParam;
import com.gitee.quiet.system.service.QuietRouteService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由信息Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/route")
@PreAuthorize(value = "hasRole('SystemAdmin')")
public class QuietRouteController {
    
    private final QuietRouteService routeService;
    
    public QuietRouteController(QuietRouteService routeService) {
        this.routeService = routeService;
    }
    
    
    /**
     * 分页查询网关路由.
     *
     * @return 查询所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietRoute>> page(@RequestBody QuietRouteParam param) {
        return Result.success(routeService.page(param.getParams(), param.page()));
    }
    
    /**
     * 新增网关路由.
     *
     * @param param :save 新增的网关路由信息
     * @return 新增后的网关路由信息
     */
    @PostMapping("/save")
    public Result<QuietRoute> save(@RequestBody @Validated(Create.class) QuietRouteParam param) {
        return Result.createSuccess(routeService.save(param.getSave()));
    }
    
    /**
     * 删除网关路由.
     *
     * @param param :deleteId 删除的网关路由ID
     * @return Result
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietRouteParam param) {
        routeService.delete(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新网关路由.
     *
     * @param param :update 更新的网关路由信息
     * @return 新增后的网关路由信息
     */
    @PostMapping("/update")
    public Result<QuietRoute> update(@RequestBody @Validated(Update.class) QuietRouteParam param) {
        return Result.updateSuccess(routeService.update(param.getUpdate()));
    }
    
    @PostMapping("/removePredicate")
    public Result<QuietRoute> removePredicate(@RequestBody QuietRouteParam param) {
        return Result.success(routeService.changePredicate(param.getId(), param.getRoutePredicate(), Operation.DELETE));
    }
    
    @PostMapping("/removeFilter")
    public Result<QuietRoute> removeFilter(@RequestBody QuietRouteParam param) {
        return Result.success(routeService.changeFilter(param.getId(), param.getRouteFilter(), Operation.DELETE));
    }
}
