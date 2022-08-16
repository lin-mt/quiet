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

import com.gitee.quiet.scrum.convert.ScrumDemandConvert;
import com.gitee.quiet.scrum.dto.ScrumDemandDTO;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.vo.ScrumDemandVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.IdValid;
import com.gitee.quiet.validation.groups.OffsetLimitValid;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 需求Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demand")
public class ScrumDemandController {

  private final ScrumDemandService demandService;

  private final ScrumDemandConvert demandConvert;

  /**
   * 删除需求
   *
   * @param id 删除的需求ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    demandService.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 滚动查询待规划的需求
   *
   * @param dto :id 项目ID ：demandFilter 需求过滤条件
   * @return 待规划的需求
   */
  @GetMapping("/scroll-to-be-planned")
  public Result<List<ScrumDemandVO>> scrollToBePlanned(
      @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandDTO dto) {
    List<ScrumDemand> scrumDemands =
        demandService.listToBePlanned(
            dto.getId(), dto.getDemandFilter(), dto.getOffset(), dto.getLimit());
    return Result.success(demandConvert.entities2vos(scrumDemands));
  }

  /**
   * 滚动查询迭代的需求
   *
   * @param dto :id 迭代ID
   * @return 处于该迭代的需求
   */
  @GetMapping("/scroll-by-iteration-id")
  public Result<List<ScrumDemandVO>> scrollByIterationId(
      @Validated({OffsetLimitValid.class, IdValid.class}) ScrumDemandDTO dto) {
    List<ScrumDemand> scrumDemands =
        demandService.scrollIteration(dto.getId(), dto.getOffset(), dto.getLimit());
    return Result.success(demandConvert.entities2vos(scrumDemands));
  }

  /**
   * 创建需求
   *
   * @param dto 创建的需求信息
   * @return 创建后的需求信息
   */
  @PostMapping
  public Result<ScrumDemandVO> save(@RequestBody @Validated(Create.class) ScrumDemandDTO dto) {
    ScrumDemand save = demandService.save(demandConvert.dto2entity(dto));
    return Result.success(demandConvert.entity2vo(save));
  }

  /**
   * 更新需求
   *
   * @param dto 更新的需求信息
   * @return 更新后的需求信息
   */
  @PutMapping
  public Result<ScrumDemandVO> update(@RequestBody @Validated(Update.class) ScrumDemandDTO dto) {
    ScrumDemand update = demandService.update(demandConvert.dto2entity(dto));
    return Result.success(demandConvert.entity2vo(update));
  }

  /**
   * 查询一个迭代下的所有需求信息
   *
   * @param id 迭代ID
   * @return 需求信息
   */
  @GetMapping("/all/{id}")
  public Result<List<ScrumDemandVO>> all(@PathVariable Long id) {
    List<ScrumDemand> scrumDemands = demandService.findAllByIterationId(id);
    return Result.success(demandConvert.entities2vos(scrumDemands));
  }

  /**
   * 分页查询需求信息
   *
   * @param dto 查询参数
   * @return 查询结果
   */
  @GetMapping("/page")
  public Result<Page<ScrumDemandVO>> page(ScrumDemandDTO dto) {
    Page<ScrumDemand> page = demandService.page(demandConvert.dto2entity(dto), dto.page());
    return Result.success(demandConvert.page2page(page));
  }
}
