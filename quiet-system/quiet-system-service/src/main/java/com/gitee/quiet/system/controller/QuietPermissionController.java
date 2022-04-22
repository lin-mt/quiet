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
import com.gitee.quiet.system.vo.QuietPermissionVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Result<Page<QuietPermissionVO>> page(QuietPermissionDTO dto) {
        Page<QuietPermission> permissionPage = permissionService.page(permissionConvert.dto2entity(dto), dto.page());
        return Result.success(permissionConvert.page2page(permissionPage));
    }

    /**
     * 新增权限配置.
     *
     * @param dto 新增的权限配置信息
     * @return 新增的权限信息
     */
    @PostMapping
    public Result<QuietPermissionVO> save(@RequestBody @Validated(Create.class) QuietPermissionDTO dto) {
        QuietPermission permission = permissionService.saveOrUpdate(permissionConvert.dto2entity(dto));
        return Result.createSuccess(permissionConvert.entity2vo(permission));
    }

    /**
     * 更新权限配置.
     *
     * @param dto 更新的权限配置信息
     * @return 更新的权限信息
     */
    @PutMapping
    public Result<QuietPermissionVO> update(@RequestBody @Validated(Update.class) QuietPermissionDTO dto) {
        QuietPermission permission = permissionService.saveOrUpdate(permissionConvert.dto2entity(dto));
        return Result.updateSuccess(permissionConvert.entity2vo(permission));
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
