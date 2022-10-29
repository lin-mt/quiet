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

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocApiGroupConvert;
import com.gitee.quiet.doc.dto.DocApiGroupDTO;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.manager.DocApiGroupManager;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.vo.DocApiGroupVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 接口分组信息Api.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api-group")
public class DocApiGroupController {

  private final DocApiGroupService apiGroupService;
  private final DocApiGroupManager apiGroupManager;
  private final DocApiGroupConvert apiGroupConvert;

  /**
   * 新建接口分组
   *
   * @param dto 新建的接口分组信息
   * @return 新增的接口分组信息
   */
  @PostMapping
  public Result<DocApiGroupVO> save(@RequestBody @Validated(Create.class) DocApiGroupDTO dto) {
    DocApiGroup save = apiGroupService.save(apiGroupConvert.dto2entity(dto));
    return Result.createSuccess(apiGroupConvert.entity2vo(save));
  }

  /**
   * 更新接口分组信息
   *
   * @param dto 更新的接口分组信息
   * @return 更新后的接口分组信息
   */
  @PutMapping
  public Result<DocApiGroupVO> update(@RequestBody @Validated(Update.class) DocApiGroupDTO dto) {
    DocApiGroup update = apiGroupService.update(apiGroupConvert.dto2entity(dto));
    return Result.updateSuccess(apiGroupConvert.entity2vo(update));
  }

  /**
   * 根据接口分组ID删除接口分组ID
   *
   * @param id 要删除的接口分组ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    apiGroupManager.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 根据接口分组ID获取分组信息
   *
   * @param id 接口分组ID
   * @return 接口分组信息
   */
  @GetMapping("/{id}")
  public Result<DocApiGroupVO> get(@PathVariable Long id) {
    DocApiGroup apiGroup = apiGroupService.findById(id);
    return Result.success(apiGroupConvert.entity2vo(apiGroup));
  }

  /**
   * 根据项目ID和接口分组名称模糊查询接口分组信息
   *
   * @param projectId 项目ID
   * @param name 分组名称
   * @param limit 限制查询条数，小于等于0或者不传则查询所有
   * @return 接口分组信息
   */
  @GetMapping("/list")
  public Result<List<DocApiGroupVO>> list(
      @RequestParam Long projectId,
      @RequestParam(required = false) Set<Long> ids,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long limit) {
    List<DocApiGroup> docApiGroups = apiGroupService.listByProjectIdAndName(projectId, ids, name, limit);
    return Result.success(apiGroupConvert.entities2vos(docApiGroups));
  }
}
