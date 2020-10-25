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

package com.gitee.quite.system.controller;

import com.gitee.quite.common.service.base.PostParam;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.entity.QuitePermission;
import com.gitee.quite.system.service.QuitePermissionService;
import com.querydsl.core.QueryResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class QuitePermissionController {
    
    private final QuitePermissionService permissionService;
    
    public QuitePermissionController(QuitePermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    /**
     * 查询权限信息.
     *
     * @return 查询的权限配置信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuitePermission>> page(@RequestBody QuitePermissionPostParam postParam) {
        return Result.success(permissionService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增权限配置.
     *
     * @param postParam :save 新增的权限配置信息
     * @return 新增的权限信息
     */
    @PostMapping("/save")
    public Result<QuitePermission> save(@RequestBody @Validated(Create.class) QuitePermissionPostParam postParam) {
        return Result.success(permissionService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新权限配置.
     *
     * @param postParam :update 更新的权限配置信息
     * @return 更新的权限信息
     */
    @PostMapping("/update")
    public Result<QuitePermission> Update(@RequestBody @Validated(Update.class) QuitePermissionPostParam postParam) {
        return Result.success(permissionService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 删除权限配置.
     *
     * @param postParam :deleteId 要删除的权限配置信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuitePermissionPostParam postParam) {
        if (permissionService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    static class QuitePermissionPostParam extends PostParam<QuitePermission, QuitePermission> {
    
    }
}
