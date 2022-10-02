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

import com.gitee.quiet.scrum.convert.ScrumTaskStepConvert;
import com.gitee.quiet.scrum.dto.ScrumTaskStepDTO;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.manager.ScrumTaskStepManager;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.scrum.vo.ScrumTaskStepVO;
import com.gitee.quiet.service.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任务步骤 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/task-step")
public class ScrumTaskStepController {

  private final ScrumTaskStepService taskStepService;
  private final ScrumTaskStepManager taskStepManager;
  private final ScrumTaskStepConvert taskStepConvert;

  /**
   * 批量更新任务步骤
   *
   * @param templateId 模板ID
   * @param dto 批量更新的步骤信息
   * @return 更新结果
   */
  @PostMapping("/batch")
  public Result<List<ScrumTaskStepVO>> saveBatch(
      @RequestParam Long templateId, @RequestBody List<@Valid ScrumTaskStepDTO> dto) {
    List<ScrumTaskStep> batch =
        taskStepManager.saveBatch(
            templateId, dto.stream().map(taskStepConvert::dto2entity).collect(Collectors.toList()));
    return Result.success(taskStepConvert.entities2vos(batch));
  }

  /**
   * 根据模板ID查询该模板ID下的所有任务步骤配置信息
   *
   * @param templateId 模板ID
   * @return 模板下的所有任务步骤配置信息
   */
  @GetMapping("/list")
  public Result<List<ScrumTaskStepVO>> list(@RequestParam Long templateId) {
    List<ScrumTaskStep> taskSteps = taskStepService.list(templateId);
    return Result.success(taskStepConvert.entities2vos(taskSteps));
  }
}
