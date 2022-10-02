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

import com.gitee.quiet.scrum.convert.ScrumPriorityConvert;
import com.gitee.quiet.scrum.dto.ScrumPriorityDTO;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.manager.ScrumPriorityManager;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.vo.ScrumPriorityVO;
import com.gitee.quiet.service.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先级 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/priority")
public class ScrumPriorityController {

  private final ScrumPriorityService priorityService;
  private final ScrumPriorityManager priorityManager;
  private final ScrumPriorityConvert priorityConvert;

  /**
   * 批量保存/更新优先级信息
   *
   * @param priorities 批量保存/更新的优先级信息
   * @return 保存后的优先级信息
   */
  @PostMapping("/batch")
  public Result<List<ScrumPriorityVO>> saveBatch(
      @RequestBody @NotEmpty List<@Valid ScrumPriorityDTO> priorities,
      @RequestParam Long templateId) {
    List<ScrumPriority> batch =
        priorityManager.saveBatch(
            templateId,
            priorities.stream().map(priorityConvert::dto2entity).collect(Collectors.toList()));
    return Result.success(priorityConvert.entities2vos(batch));
  }

  /**
   * 根据模板ID查询该模板ID下的所有优先级配置信息
   *
   * @param templateId 模板ID
   * @return 模板下的所有优先级配置信息
   */
  @GetMapping("/list")
  public Result<List<ScrumPriorityVO>> list(@RequestParam Long templateId) {
    List<ScrumPriority> priorities = priorityService.list(templateId);
    return Result.success(priorityConvert.entities2vos(priorities));
  }
}
