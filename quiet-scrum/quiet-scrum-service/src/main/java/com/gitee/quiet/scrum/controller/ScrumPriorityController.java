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
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.vo.ScrumPriorityVO;
import com.gitee.quiet.service.dto.ValidListDTO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先级 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/priority")
public class ScrumPriorityController {

  private final ScrumPriorityService priorityService;

  private final ScrumPriorityConvert priorityConvert;

  /**
   * 新增优先级选项
   *
   * @param dto 新增的优先级信息
   * @return 新增后的优先级信息
   */
  @PostMapping
  public Result<ScrumPriorityVO> save(@RequestBody @Validated(Create.class) ScrumPriorityDTO dto) {
    ScrumPriority save = priorityService.save(priorityConvert.dto2entity(dto));
    return Result.createSuccess(priorityConvert.entity2vo(save));
  }

  /**
   * 更新优先级选项信息
   *
   * @param dto 更新的优先级信息
   * @return 更新后的优先级信息
   */
  @PutMapping
  public Result<ScrumPriorityVO> update(
      @RequestBody @Validated(Update.class) ScrumPriorityDTO dto) {
    ScrumPriority update = priorityService.update(priorityConvert.dto2entity(dto));
    return Result.updateSuccess(priorityConvert.entity2vo(update));
  }

  /**
   * 删除优先级信息
   *
   * @param id 删除的优先级ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    priorityService.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 批量更新优先级信息
   *
   * @param dto :data 批量更新的优先级信息
   * @return 更新结果
   */
  @PutMapping("/batch")
  public Result<List<ScrumPriorityVO>> updateBatch(
      @RequestBody @Validated(Update.class) ValidListDTO<ScrumPriorityDTO> dto) {
    List<ScrumPriority> batch =
        priorityService.updateBatch(
            dto.getData().stream().map(priorityConvert::dto2entity).collect(Collectors.toList()));
    return Result.success(priorityConvert.entities2vos(batch));
  }

  /**
   * 根据模板ID查询该模板ID下的所有优先级配置信息
   *
   * @param id 模板ID
   * @return 模板下的所有优先级配置信息
   */
  @GetMapping("/all-by-template-id/{id}")
  public Result<List<ScrumPriorityVO>> allByTemplateId(@PathVariable Long id) {
    List<ScrumPriority> priorities = priorityService.findAllByTemplateId(id);
    return Result.success(priorityConvert.entities2vos(priorities));
  }
}
