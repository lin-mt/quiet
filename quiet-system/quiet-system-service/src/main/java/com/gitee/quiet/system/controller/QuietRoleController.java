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
import com.gitee.quiet.system.convert.QuietRoleConvert;
import com.gitee.quiet.system.dto.QuietRoleDTO;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.vo.QuietRoleVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * 角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class QuietRoleController {

    private final QuietRoleService roleService;

    private final QuietRoleConvert roleConvert;

    /**
     * 以树形结构查询角色之间的关联信息.
     *
     * @return 角色之间的关联关系
     */
    @GetMapping("/tree")
    public Result<List<QuietRoleVO>> tree() {
        List<QuietRole> treeRoles = roleService.tree();
        return Result.success(roleConvert.entities2vos(treeRoles));
    }

    /**
     * 分页查询角色.
     *
     * @return 查询所有信息
     */
    @GetMapping("/page")
    public Result<Page<QuietRoleVO>> page(QuietRoleDTO dto) {
        Page<QuietRole> rolePage = roleService.page(roleConvert.dto2entity(dto), dto.page());
        return Result.success(roleConvert.page2page(rolePage));
    }

    /**
     * 新增角色.
     *
     * @param dto 新增的角色信息
     * @return 新增后的角色信息
     */
    @PostMapping
    public Result<QuietRoleVO> save(@RequestBody @Validated(Create.class) QuietRoleDTO dto) {
        QuietRole save = roleService.save(roleConvert.dto2entity(dto));
        return Result.createSuccess(roleConvert.entity2vo(save));
    }

    /**
     * 删除角色.
     *
     * @param id 删除的角色ID
     * @return Result
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.deleteSuccess();
    }

    /**
     * 更新角色.
     *
     * @param dto 更新的角色信息
     * @return 新增后的角色信息
     */
    @PutMapping
    public Result<QuietRoleVO> update(@RequestBody @Validated(Update.class) QuietRoleDTO dto) {
        QuietRole update = roleService.update(roleConvert.dto2entity(dto));
        return Result.updateSuccess(roleConvert.entity2vo(update));
    }

}
