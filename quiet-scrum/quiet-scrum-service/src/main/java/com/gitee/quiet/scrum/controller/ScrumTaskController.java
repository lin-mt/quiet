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

import com.gitee.quiet.scrum.convert.ScrumTaskConvert;
import com.gitee.quiet.scrum.dto.ScrumTaskDTO;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.manager.ScrumTaskManager;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.vo.ScrumTaskVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 任务Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class ScrumTaskController {

  private final ScrumTaskService taskService;
  private final ScrumTaskManager taskManager;
  private final ScrumTaskConvert taskConvert;

  /**
   * 查询任务信息
   *
   * @param demandIds 需求ID集合
   * @param executorIds 执行者ID集合
   * @return 任务集合
   */
  @GetMapping("/list")
  public Result<List<ScrumTaskVO>> list(@RequestParam(required = false) Set<Long> demandIds,
                                        @RequestParam(required = false) Set<Long> executorIds) {
    List<ScrumTask> tasks = taskService.list(demandIds, executorIds);
    return Result.success(taskConvert.entities2vos(tasks));
  }

  /**
   * 创建任务
   *
   * @param dto 创建的任务信息
   * @return 创建后的任务信息
   */
  @PostMapping
  public Result<ScrumTaskVO> save(@RequestBody @Validated(Create.class) ScrumTaskDTO dto) {
    ScrumTask save = taskManager.saveOrUpdate(taskConvert.dto2entity(dto));
    return Result.createSuccess(taskConvert.entity2vo(save));
  }

  /**
   * 更新任务
   *
   * @param dto 更新的任务信息
   * @return 更新后的任务信息
   */
  @PutMapping
  public Result<ScrumTaskVO> update(@RequestBody @Validated(Update.class) ScrumTaskDTO dto) {
    ScrumTask update = taskManager.saveOrUpdate(taskConvert.dto2entity(dto));
    return Result.updateSuccess(taskConvert.entity2vo(update));
  }

  /**
   * 删除任务
   *
   * @param id 删除的任务ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    taskService.deleteById(id);
    return Result.deleteSuccess();
  }
}
