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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.params.QuietRoleParam;
import com.gitee.quiet.system.service.QuietRoleService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/role")
public class QuietRoleController {
    
    private final QuietRoleService roleService;
    
    public QuietRoleController(QuietRoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * 以树形结构查询角色之间的关联信息.
     *
     * @return 角色之间的关联关系
     */
    @PostMapping("/tree")
    public Result<List<QuietRole>> tree() {
        return Result.success(roleService.tree());
    }
    
    /**
     * 分页查询角色.
     *
     * @return 查询所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietRole>> page(@RequestBody QuietRoleParam postParam) {
        return Result.success(roleService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增角色.
     *
     * @param postParam :save 新增的角色信息
     * @return 新增后的角色信息
     */
    @PostMapping("/save")
    public Result<QuietRole> save(@RequestBody @Validated(Create.class) QuietRoleParam postParam) {
        return Result.createSuccess(roleService.save(postParam.getSave()));
    }
    
    /**
     * 删除角色.
     *
     * @param postParam :deleteId 删除的角色ID
     * @return Result
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietRoleParam postParam) {
        roleService.deleteRole(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新角色.
     *
     * @param postParam :update 更新的角色信息
     * @return 新增后的角色信息
     */
    @PostMapping("/update")
    public Result<QuietRole> update(@RequestBody @Validated(Update.class) QuietRoleParam postParam) {
        return Result.updateSuccess(roleService.update(postParam.getUpdate()));
    }
    
}
