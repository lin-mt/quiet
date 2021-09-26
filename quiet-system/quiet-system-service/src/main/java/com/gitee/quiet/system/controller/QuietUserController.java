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
import com.gitee.quiet.service.security.entity.QuietUserDetails;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.gitee.quiet.system.convert.QuietUserConvert;
import com.gitee.quiet.system.dto.QuietUserDTO;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.gitee.quiet.validation.util.ValidationUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class QuietUserController {
    
    private final QuietUserService userService;
    
    private final QuietUserRoleService userRoleService;
    
    private final QuietUserConvert userConvert;
    
    /**
     * 根据用户名/全名查询用户信息
     *
     * @param keyword 用户名/全名
     * @return 用户信息
     */
    @GetMapping("/listUsersByName")
    public Result<List<QuietUser>> listUsersByName(@RequestParam String keyword) {
        return Result.success(userService.listUsersByName(keyword, 9));
    }
    
    /**
     * 用户注册.
     *
     * @param dto 用户信息
     * @return 注册后的用户信息
     */
    @PostMapping
    public Result<QuietUser> create(@RequestBody @Validated(Create.class) QuietUserDTO dto) {
        // TODO 可以根据配置确定是否注册就直接启用该用户
        return Result.success(userService.save(userConvert.dtoToEntity(dto)));
    }
    
    /**
     * 分页查询用户.
     *
     * @param dto 查询参数
     * @return 查询的用户信息
     */
    @GetMapping("/page")
    public Result<QueryResults<QuietUser>> page(QuietUserDTO dto) {
        return Result.success(userService.page(userConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 删除用户.
     *
     * @param id 要删除的用户ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@PathVariable Long id) {
        userService.delete(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 更新用户.
     *
     * @param dto :update 要更新的用户信息
     * @return 更新后的用户信息
     */
    @PutMapping
    @PreAuthorize(value = "#dto.id == authentication.principal.id || hasRole('Admin')")
    public Result<QuietUser> update(@RequestBody @Validated(Update.class) QuietUserDTO dto) {
        return Result.updateSuccess(userService.update(userConvert.dtoToEntity(dto)));
    }
    
    /**
     * 获取当前登陆人信息.
     *
     * @return 当前登陆人信息
     */
    @GetMapping("/currentUserInfo")
    public Result<QuietUserDetails> currentUserInfo() {
        return Result.success(CurrentUserUtil.get());
    }
    
    /**
     * 移除用户的角色
     *
     * @param dto :id 移除角色的用户的ID :roleId 移除的角色ID
     * @return 移除结果
     */
    @PostMapping("/removeRole")
    public Result<Object> removeRole(@RequestBody QuietUserDTO dto) {
        ValidationUtils.notNull(dto.getId(), "userRole.useId.not.null");
        ValidationUtils.notNull(dto.getRoleId(), "userRole.roleId.not.null");
        userRoleService.deleteUserRole(dto.getId(), dto.getRoleId());
        return Result.deleteSuccess();
    }
    
    /**
     * 添加用户的角色
     *
     * @param dto :id 添加角色的用户的ID :roleId 添加的角色ID
     * @return 移除结果
     */
    @PostMapping("/addRoles")
    public Result<List<QuietUserRole>> addRoles(@RequestBody QuietUserDTO dto) {
        return Result.createSuccess(userRoleService.addRoles(dto.getUserRoles()));
    }
    
}
