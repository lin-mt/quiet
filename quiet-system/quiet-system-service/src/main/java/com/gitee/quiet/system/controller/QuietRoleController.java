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
import com.gitee.quiet.system.convert.QuietRoleConvert;
import com.gitee.quiet.system.dto.QuietRoleDTO;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.manager.QuietRoleManager;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.vo.QuietRoleVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
  private final QuietRoleManager roleManager;
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
    Page<QuietRoleVO> roles = roleConvert.page2page(rolePage);
    if (!roles.isEmpty()) {
      Set<Long> parentIds =
              roles.getContent().stream()
                      .map(QuietRoleVO::getParentId)
                      .filter(Objects::nonNull)
                      .collect(Collectors.toSet());
      if (CollectionUtils.isNotEmpty(parentIds)) {
        Map<Long, QuietRole> idToRoleInfo =
                roleService.findAllById(parentIds).stream()
                        .collect(Collectors.toMap(QuietRole::getId, role -> role));
        for (QuietRoleVO role : roles.getContent()) {
          if (role.getParentId() != null) {
            role.setParentRoleName(idToRoleInfo.get(role.getParentId()).getRoleName());
          }
        }
      }
    }
    return Result.success(roles);
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
    roleManager.deleteRole(id);
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
