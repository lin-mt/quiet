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

package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.scrum.convert.ScrumProjectConvert;
import com.gitee.quiet.scrum.dto.ScrumProjectDTO;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.manager.ScrumProjectManager;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.vo.ScrumProjectVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ScrumProjectController {

  private final ScrumProjectService projectService;
  private final ScrumProjectManager projectManager;
  private final ScrumProjectConvert projectConvert;

  /**
   * 获取项目信息
   *
   * @param id 项目ID
   * @return 项目信息
   */
  @GetMapping("/{id}")
  public Result<ScrumProjectVO> projectInfo(@PathVariable Long id) {
    ScrumProject project = projectService.projectInfo(id);
    return Result.success(projectConvert.entity2vo(project));
  }

  /**
   * 获取项目信息
   *
   * @param groupId 项目组ID
   * @return 项目信息
   */
  @GetMapping("/list")
  public Result<List<ScrumProjectVO>> list(@RequestParam(required = false) Long groupId) {
    List<ScrumProject> projects = projectService.list(groupId);
    return Result.success(projectConvert.entities2vos(projects));
  }

  /**
   * 新增项目
   *
   * @param dto 新增的项目信息
   * @return 新增后的项目信息
   */
  @PostMapping
  public Result<ScrumProjectVO> save(@RequestBody @Validated(Create.class) ScrumProjectDTO dto) {
    ScrumProject save = projectManager.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.createSuccess(projectConvert.entity2vo(save));
  }

  /**
   * 更新项目
   *
   * @param dto 更新的项目信息
   * @return 更新后的项目信息
   */
  @PutMapping
  public Result<ScrumProjectVO> update(@RequestBody @Validated(Update.class) ScrumProjectDTO dto) {
    ScrumProject update = projectManager.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.updateSuccess(projectConvert.entity2vo(update));
  }

  /**
   * 删除项目
   *
   * @param id 删除的项目ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectManager.deleteById(id);
    return Result.deleteSuccess();
  }
}
