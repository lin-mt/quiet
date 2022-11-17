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

import com.gitee.quiet.doc.converter.DocProjectConvert;
import com.gitee.quiet.doc.dto.DocProjectDTO;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.manager.DocProjectManager;
import com.gitee.quiet.doc.service.DocProjectService;
import com.gitee.quiet.doc.vo.DocProjectVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * Project Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class DocProjectController {

  private final DocProjectService projectService;
  private final DocProjectManager projectManager;

  private final DocProjectConvert projectConvert;

  /**
   * 根据项目名称、项目ID集合查询项目信息
   *
   * @param name 项目名称
   * @param ids 项目ID集合
   * @param limit 限制查询条数，小于等于0或者不传则查询 15 条信息
   * @return 项目信息
   */
  @GetMapping("/list")
  public Result<List<DocProjectVO>> list(
      @RequestParam(required = false) Long groupId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Set<Long> ids,
      @RequestParam(required = false) Long limit) {
    List<DocProject> docProjects = projectService.list(groupId, name, ids, limit);
    return Result.success(projectConvert.entities2vos(docProjects));
  }

  /**
   * 根据项目分组ID获取项目信息
   *
   * @param groupId 项目分组ID，小于等于0则查询创建人为当前登录人，且未分组的项目
   * @return 项目信息
   */
  @GetMapping("/project-group/{groupId}")
  public Result<List<DocProject>> projectGroup(@PathVariable Long groupId) {
    return Result.success(projectService.listAllByGroupId(groupId));
  }

  /**
   * 根据项目ID查询项目信息
   *
   * @param id 项目ID
   * @return 项目信息
   */
  @GetMapping("/{id}")
  public Result<DocProjectVO> projectInfo(@PathVariable Long id) {
    DocProject docProject = projectService.getById(id);
    return Result.success(projectConvert.entity2vo(docProject));
  }

  /**
   * 新建项目文档
   *
   * @param dto 新建的项目信息
   * @return 新增的项目信息
   */
  @PostMapping
  public Result<DocProjectVO> save(@RequestBody @Validated(Create.class) DocProjectDTO dto) {
    DocProject save = projectService.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.createSuccess(projectConvert.entity2vo(save));
  }

  /**
   * 更新项目信息
   *
   * @param dto 更新的项目信息
   * @return 更新后的项目信息
   */
  @PutMapping
  public Result<DocProjectVO> update(@RequestBody @Validated(Update.class) DocProjectDTO dto) {
    DocProject update = projectService.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.updateSuccess(projectConvert.entity2vo(update));
  }

  /**
   * 根据项目ID删除项目信息
   *
   * @param id 要删除的项目ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectService.delete(id);
    return Result.deleteSuccess();
  }

  @PutMapping("/swagger/{id}")
  public Result<DocProjectVO> swagger(
      @PathVariable @Range(min = 1L) Long id,
      @RequestParam Boolean enabled,
      @RequestParam @NotBlank String url,
      @RequestParam @NotBlank String cron) {
    DocProject project = projectManager.updateSwagger(id, enabled, url, cron);
    return Result.success(projectConvert.entity2vo(project));
  }
}
