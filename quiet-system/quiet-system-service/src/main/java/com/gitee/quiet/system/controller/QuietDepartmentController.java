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
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietDepartment;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.params.QuietDepartmentParam;
import com.gitee.quiet.system.params.QuietUserParam;
import com.gitee.quiet.system.service.QuietDepartmentService;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.querydsl.core.QueryResults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/department")
public class QuietDepartmentController {
    
    private final QuietDepartmentService departmentService;
    
    private final QuietDepartmentUserService departmentUserService;
    
    public QuietDepartmentController(QuietDepartmentService departmentService,
            QuietDepartmentUserService departmentUserService) {
        this.departmentService = departmentService;
        this.departmentUserService = departmentUserService;
    }
    
    /**
     * 部门添加成员信息.
     *
     * @return 删除的部门用户信息
     */
    @PostMapping("/removeUsers")
    public Result<Object> removeUsers(@RequestBody @Validated(IdValid.class) QuietDepartmentParam param) {
        departmentUserService.removeUsers(param.getId(), param.getUserIds());
        return Result.success();
    }
    
    /**
     * 部门添加成员信息.
     *
     * @return 添加部门用户信息
     */
    @PostMapping("/addUsers")
    public Result<Object> addUsers(@RequestBody @Validated(IdValid.class) QuietDepartmentParam param) {
        departmentUserService.addUsers(param.getId(), param.getUserIds());
        return Result.success();
    }
    
    /**
     * 分页查询部门的用户信息.
     *
     * @return 查询的部门用户信息
     */
    @PostMapping("/pageUser")
    public Result<QueryResults<QuietUser>> pageUser(@RequestBody QuietUserParam param) {
        return Result.success(departmentService.pageUser(param.getDepartmentId(), param.getParams(), param.page()));
    }
    
    /**
     * 分页查询部门信息.
     *
     * @return 查询的部门信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietDepartment>> page(@RequestBody QuietDepartmentParam param) {
        return Result.success(departmentService.page(param.getParams(), param.page()));
    }
    
    /**
     * 获取所有部门的树形结构信息.
     *
     * @return 查询的部门信息
     */
    @PostMapping("/tree")
    public Result<List<QuietDepartment>> tree() {
        return Result.success(departmentService.tree());
    }
    
    /**
     * 新增部门.
     *
     * @param param :save 新增的部门信息
     * @return 新增的部门信息
     */
    @PostMapping("/save")
    public Result<QuietDepartment> save(@RequestBody @Validated(Create.class) QuietDepartmentParam param) {
        return Result.createSuccess(departmentService.saveOrUpdate(param.getSave()));
    }
    
    /**
     * 更新部门信息.
     *
     * @param param :update 更新的部门信息
     * @return 更新的部门信息
     */
    @PostMapping("/update")
    public Result<QuietDepartment> update(@RequestBody @Validated(Update.class) QuietDepartmentParam param) {
        return Result.updateSuccess(departmentService.saveOrUpdate(param.getUpdate()));
    }
    
    /**
     * 删除部门信息.
     *
     * @param param :deleteId 要删除的部门的ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietDepartmentParam param) {
        departmentService.delete(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
}
