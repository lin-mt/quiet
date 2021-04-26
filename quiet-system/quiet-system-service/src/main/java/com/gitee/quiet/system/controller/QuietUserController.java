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
import com.gitee.quiet.common.service.jpa.entity.QuietUserDetails;
import com.gitee.quiet.common.service.util.CurrentUserUtil;
import com.gitee.quiet.common.validation.group.param.ParamsValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.batch.CreateBatch;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.common.validation.util.ValidationUtils;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.params.QuietUserParam;
import com.gitee.quiet.system.params.QuietUserRoleParam;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/user")
public class QuietUserController {
    
    private final QuietUserService userService;
    
    private final QuietUserRoleService userRoleService;
    
    public QuietUserController(QuietUserService userService, QuietUserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }
    
    @PostMapping("/listUsersByName")
    public Result<List<QuietUser>> listUsersByName(@RequestBody QuietUserParam postParam) {
        return Result.success(userService.listUsersByName(postParam.getName(), 9));
    }
    
    /**
     * 用户注册.
     *
     * @param postParam :save 用户信息
     * @return 注册后的用户信息
     */
    @PostMapping("/registered")
    public Result<QuietUser> register(@RequestBody @Validated(Create.class) QuietUserParam postParam) {
        // TODO 可以根据配置确定是否注册就直接启用该用户
        return Result.success(userService.save(postParam.getSave()));
    }
    
    /**
     * 分页查询用户.
     *
     * @return 查询的用户信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietUser>> page(@RequestBody QuietUserParam postParam) {
        return Result.success(userService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 删除用户.
     *
     * @param postParam :deleteId 要删除的用户ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietUserParam postParam) {
        userService.delete(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新用户.
     *
     * @param postParam :update 要更新的用户信息
     * @return 更新后的用户信息
     */
    @PostMapping("/update")
    @PreAuthorize(value = "#postParam.update.id == authentication.principal.id || hasRole('Admin')")
    public Result<QuietUser> update(@RequestBody @Validated(Update.class) QuietUserParam postParam) {
        return Result.updateSuccess(userService.update(postParam.getUpdate()));
    }
    
    /**
     * 获取当前登陆人信息.
     *
     * @return 当前登陆人信息
     */
    @PostMapping("/currentUserInfo")
    public Result<QuietUserDetails> currentUserInfo() {
        return Result.success(CurrentUserUtil.get());
    }
    
    @PostMapping("/removeRole")
    public Result<Object> removeRole(@RequestBody @Validated(ParamsValid.class) QuietUserRoleParam postParam) {
        ValidationUtils.notNull(postParam.getParams().getUserId(), "userRole.useId.not.null");
        ValidationUtils.notNull(postParam.getParams().getRoleId(), "userRole.roleId.not.null");
        userRoleService.deleteUserRole(postParam.getParams().getUserId(), postParam.getParams().getRoleId());
        return Result.deleteSuccess();
    }
    
    @PostMapping("/addRoles")
    public Result<Object> addRoles(@RequestBody @Validated(CreateBatch.class) QuietUserRoleParam postParam) {
        userRoleService.addRoles(postParam.getSaveBatch());
        return Result.createSuccess();
    }
    
}
