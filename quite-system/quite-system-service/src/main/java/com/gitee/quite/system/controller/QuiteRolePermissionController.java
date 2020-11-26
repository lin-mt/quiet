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

import com.gitee.quite.common.service.base.Param;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.entity.QuiteRolePermission;
import com.gitee.quite.system.service.QuiteRolePermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色-权限 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/rolePermission")
public class QuiteRolePermissionController {
    
    private final QuiteRolePermissionService rolePermissionService;
    
    public QuiteRolePermissionController(QuiteRolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }
    
    /**
     * 新增角色-权限信息.
     *
     * @param postParam ：save 新增的角色-权限信息
     * @return 新增的角色信息
     */
    @PostMapping("/save")
    public Result<QuiteRolePermission> save(@RequestBody @Validated(Create.class) QuiteRolePermissionParam postParam) {
        return Result.createSuccess(rolePermissionService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新角色-权限信息.
     *
     * @param postParam ：update 更新的角色-权限信息
     * @return 更新的角色信息
     */
    @PostMapping("/update")
    public Result<QuiteRolePermission> update(
            @RequestBody @Validated(Update.class) QuiteRolePermissionParam postParam) {
        return Result.updateSuccess(rolePermissionService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 删除角色-权限信息.
     *
     * @param postParam ：updateId 删除的角色-权限信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<QuiteRolePermission> delete(
            @RequestBody @Validated(DeleteSingle.class) QuiteRolePermissionParam postParam) {
        rolePermissionService.delete(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    static class QuiteRolePermissionParam extends Param<QuiteRolePermission, QuiteRolePermission> {
    
    }
    
}
