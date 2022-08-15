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
import com.gitee.quiet.system.convert.QuietDepartmentConvert;
import com.gitee.quiet.system.convert.QuietUserConvert;
import com.gitee.quiet.system.dto.QuietDepartmentDTO;
import com.gitee.quiet.system.entity.QuietDepartment;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.service.QuietDepartmentService;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import com.gitee.quiet.system.vo.QuietDepartmentVO;
import com.gitee.quiet.system.vo.QuietUserVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.IdValid;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/department")
public class QuietDepartmentController {

  private final QuietDepartmentService departmentService;

  private final QuietDepartmentUserService departmentUserService;

  private final QuietDepartmentConvert departmentConvert;

  private final QuietUserConvert userConvert;

  /**
   * 部门移除成员信息.
   *
   * @param dto :id 要移除成员信息的部门ID :userIds 移除的成员ID集合
   * @return 删除结果
   */
  @PostMapping("/remove-users")
  public Result<Object> removeUsers(@RequestBody @Validated(IdValid.class) QuietDepartmentDTO dto) {
    departmentUserService.removeUsers(dto.getId(), dto.getUserIds());
    return Result.success();
  }

  /**
   * 部门添加成员信息.
   *
   * @param dto :id 要添加成员信息的部门ID :userIds 添加的成员ID集合
   * @return 添加结果
   */
  @PostMapping("/add-users")
  public Result<Object> addUsers(@RequestBody @Validated(IdValid.class) QuietDepartmentDTO dto) {
    departmentUserService.addUsers(dto.getId(), dto.getUserIds());
    return Result.success();
  }

  /**
   * 分页查询部门的用户信息.
   *
   * @param dto 查询条件
   * @return 查询的部门的用户信息
   */
  @GetMapping("/page-user")
  public Result<QueryResults<QuietUserVO>> pageUser(QuietDepartmentDTO dto) {
    QueryResults<QuietUser> userQueryResults =
        departmentService.pageUser(dto.getId(), dto.getParams(), dto.page());
    return Result.success(userConvert.results2results(userQueryResults));
  }

  /**
   * 分页查询部门信息.
   *
   * @param dto 查询参数
   * @return 查询的部门信息
   */
  @GetMapping("/page")
  public Result<Page<QuietDepartmentVO>> page(QuietDepartmentDTO dto) {
    Page<QuietDepartment> departmentPage =
        departmentService.page(departmentConvert.dto2entity(dto), dto.page());
    return Result.success(departmentConvert.page2page(departmentPage));
  }

  /**
   * 获取所有部门的树形结构信息.
   *
   * @return 查询的部门信息
   */
  @GetMapping("/tree")
  public Result<List<QuietDepartmentVO>> tree() {
    List<QuietDepartment> tree = departmentService.tree();
    return Result.success(departmentConvert.entities2vos(tree));
  }

  /**
   * 新增部门.
   *
   * @param dto 新增的部门信息
   * @return 新增的部门信息
   */
  @PostMapping
  public Result<QuietDepartmentVO> save(
      @RequestBody @Validated(Create.class) QuietDepartmentDTO dto) {
    QuietDepartment department = departmentService.saveOrUpdate(departmentConvert.dto2entity(dto));
    return Result.createSuccess(departmentConvert.entity2vo(department));
  }

  /**
   * 更新部门信息.
   *
   * @param dto 更新的部门信息
   * @return 更新后的部门信息
   */
  @PutMapping
  public Result<QuietDepartmentVO> update(
      @RequestBody @Validated(Update.class) QuietDepartmentDTO dto) {
    QuietDepartment update = departmentService.saveOrUpdate(departmentConvert.dto2entity(dto));
    return Result.updateSuccess(departmentConvert.entity2vo(update));
  }

  /**
   * 删除部门信息.
   *
   * @param id 要删除的部门的ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    departmentService.deleteById(id);
    return Result.deleteSuccess();
  }
}
