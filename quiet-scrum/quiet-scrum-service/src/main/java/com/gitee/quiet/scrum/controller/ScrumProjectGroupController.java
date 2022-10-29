/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.scrum.convert.ScrumProjectGroupConverter;
import com.gitee.quiet.scrum.dto.ScrumProjectGroupDTO;
import com.gitee.quiet.scrum.entity.ScrumProjectGroup;
import com.gitee.quiet.scrum.manager.ScrumProjectGroupManager;
import com.gitee.quiet.scrum.service.ScrumProjectGroupService;
import com.gitee.quiet.scrum.vo.ScrumProjectGroupVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project-group")
public class ScrumProjectGroupController {
  private final ScrumProjectGroupService projectGroupService;
  private final ScrumProjectGroupManager projectGroupManager;
  private final ScrumProjectGroupConverter converter;

  /**
   * 查询所有项目分组.
   *
   * @param name 项目名称
   * @param groupIds 项目ID集合
   * @return 项目分组信息
   */
  @GetMapping("/list")
  public Result<List<ScrumProjectGroupVO>> list(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Set<Long> groupIds) {
    List<ScrumProjectGroup> projectGroups = projectGroupService.listAll(name, groupIds);
    return Result.success(converter.entities2vos(projectGroups));
  }

  /**
   * 新增项目分组.
   *
   * @param dto 新增的项目分组信息
   * @return 新增后的项目分组信息
   */
  @PostMapping
  public Result<ScrumProjectGroupVO> save(
      @RequestBody @Validated(Create.class) ScrumProjectGroupDTO dto) {
    ScrumProjectGroup save = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(save));
  }

  /**
   * 删除项目分组.
   *
   * @param id 删除的项目分组ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectGroupManager.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新项目分组.
   *
   * @param dto 更新的项目分组信息
   * @return 更新后的项目分组信息
   */
  @PutMapping
  public Result<ScrumProjectGroupVO> update(
      @RequestBody @Validated(Update.class) ScrumProjectGroupDTO dto) {
    ScrumProjectGroup update = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }
}
