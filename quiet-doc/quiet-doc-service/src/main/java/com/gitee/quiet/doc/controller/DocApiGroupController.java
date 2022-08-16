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
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.vo.DocApiGroupVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    apiGroupService.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 根据项目ID和接口名称模糊查询6条接口分组信息
   *
   * @param dto :projectId 项目ID :name 分组名称
   * @return 接口分组信息
   */
  @GetMapping("/list-by-project-id-and-name")
  public Result<List<DocApiGroupVO>> listByProjectIdAndName(DocApiGroupDTO dto) {
    List<DocApiGroup> docApiGroups =
        apiGroupService.listByProjectIdAndName(dto.getProjectId(), dto.getName(), 6L);
    return Result.success(apiGroupConvert.entities2vos(docApiGroups));
  }
}
