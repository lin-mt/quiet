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
import com.gitee.quite.system.entity.QuiteRole;
import com.gitee.quite.system.service.QuiteRoleService;
import com.querydsl.core.QueryResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/role")
public class QuiteRoleController {
    
    private final QuiteRoleService roleService;
    
    public QuiteRoleController(QuiteRoleService roleService) {
        this.roleService = roleService;
    }
    
    /**
     * 查询角色.
     *
     * @return 查询所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuiteRole>> page(@RequestBody QuiteRolePostParam postParam) {
        return Result.success(roleService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增角色.
     *
     * @param postParam :save 新增的角色信息
     * @return 新增后的角色信息
     */
    @PostMapping("/save")
    public Result<QuiteRole> save(@RequestBody @Validated(Create.class) QuiteRolePostParam postParam) {
        roleService.save(postParam.getSave());
        return Result.createSuccess();
    }
    
    /**
     * 删除角色.
     *
     * @param postParam :deleteId 删除的角色ID
     * @return Result
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuiteRolePostParam postParam) {
        if (roleService.delete(postParam.getDeleteId())) {
            return Result.deleteSuccess();
        }
        return Result.deleteFailure();
    }
    
    /**
     * 更新角色.
     *
     * @param postParam :update 更新的角色信息
     * @return 新增后的角色信息
     */
    @PutMapping("/update")
    public Result<QuiteRole> update(@RequestBody @Validated(Update.class) QuiteRolePostParam postParam) {
        roleService.update(postParam.getUpdate());
        return Result.updateSuccess();
    }
    
    static class QuiteRolePostParam extends PostParam<QuiteRole, QuiteRole> {
    
    }
}
