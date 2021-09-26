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
import com.gitee.quiet.system.convert.QuietPermissionConvert;
import com.gitee.quiet.system.dto.QuietPermissionDTO;
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.service.QuietPermissionService;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
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
 * 权限 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/permission")
public class QuietPermissionController {
    
    private final QuietPermissionService permissionService;
    
    private final QuietPermissionConvert permissionConvert;
    
    /**
     * 分页查询权限信息.
     *
     * @param dto 查询参数
     * @return 查询的权限配置信息
     */
    @GetMapping("/page")
    public Result<QueryResults<QuietPermission>> page(QuietPermissionDTO dto) {
        return Result.success(permissionService.page(permissionConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 新增权限配置.
     *
     * @param dto 新增的权限配置信息
     * @return 新增的权限信息
     */
    @PostMapping
    public Result<QuietPermission> save(@RequestBody @Validated(Create.class) QuietPermissionDTO dto) {
        return Result.createSuccess(permissionService.saveOrUpdate(permissionConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新权限配置.
     *
     * @param dto 更新的权限配置信息
     * @return 更新的权限信息
     */
    @PutMapping
    public Result<QuietPermission> update(@RequestBody @Validated(Update.class) QuietPermissionDTO dto) {
        return Result.updateSuccess(permissionService.saveOrUpdate(permissionConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除权限配置.
     *
     * @param id 要删除的权限配置信息的ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return Result.deleteSuccess();
    }
    
}
