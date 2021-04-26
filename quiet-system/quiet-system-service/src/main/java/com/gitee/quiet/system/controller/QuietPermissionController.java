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

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.params.QuietPermissionParam;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.querydsl.core.QueryResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/permission")
public class QuietPermissionController {
    
    private final QuietPermissionService permissionService;
    
    public QuietPermissionController(QuietPermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    /**
     * 分页查询权限信息.
     *
     * @return 查询的权限配置信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietPermission>> page(@RequestBody QuietPermissionParam param) {
        return Result.success(permissionService.page(param.getParams(), param.page()));
    }
    
    /**
     * 新增权限配置.
     *
     * @param param :save 新增的权限配置信息
     * @return 新增的权限信息
     */
    @PostMapping("/save")
    public Result<QuietPermission> save(@RequestBody @Validated(Create.class) QuietPermissionParam param) {
        return Result.createSuccess(permissionService.saveOrUpdate(param.getSave()));
    }
    
    /**
     * 更新权限配置.
     *
     * @param param :update 更新的权限配置信息
     * @return 更新的权限信息
     */
    @PostMapping("/update")
    public Result<QuietPermission> update(@RequestBody @Validated(Update.class) QuietPermissionParam param) {
        return Result.updateSuccess(permissionService.saveOrUpdate(param.getUpdate()));
    }
    
    /**
     * 删除权限配置.
     *
     * @param param :deleteId 要删除的权限配置信息的ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietPermissionParam param) {
        permissionService.delete(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
}
