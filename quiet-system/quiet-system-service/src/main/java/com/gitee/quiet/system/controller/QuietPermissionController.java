/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
import org.springframework.web.bind.annotation.*;

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
    Page<QuietPermission> permissionPage =
        permissionService.page(permissionConvert.dto2entity(dto), dto.page());
    return Result.success(permissionConvert.page2page(permissionPage));
  }

  /**
   * 新增权限配置.
   *
   * @param dto 新增的权限配置信息
   * @return 新增的权限信息
   */
  @PostMapping
  public Result<QuietPermissionVO> save(
      @RequestBody @Validated(Create.class) QuietPermissionDTO dto) {
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
  public Result<QuietPermissionVO> update(
      @RequestBody @Validated(Update.class) QuietPermissionDTO dto) {
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
