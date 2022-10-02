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

import com.gitee.quiet.scrum.convert.ScrumTemplateConvert;
import com.gitee.quiet.scrum.dto.ScrumTemplateDTO;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.manager.ScrumTemplateManager;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.scrum.vo.ScrumTemplateVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目模板Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/template")
public class ScrumTemplateController {

  private final ScrumTemplateService templateService;
  private final ScrumTemplateConvert templateConvert;
  private final ScrumTemplateManager templateManager;

  /**
   * 根据模板id、名称、状态查询模板信息
   *
   * @param id 模板ID
   * @param name 模板名称
   * @param enabled 模板状态
   * @param limit 查询数量，默认查询 15 条数据，传 0 则查询所有
   * @return 模板信息
   */
  @GetMapping("/list")
  public Result<List<ScrumTemplateVO>> list(
      @RequestParam(required = false) Long id,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Boolean enabled,
      @RequestParam(required = false) Long limit) {
    List<ScrumTemplate> templates = templateService.list(id, name, enabled, limit);
    return Result.success(templateConvert.entities2vos(templates));
  }

  /**
   * 获取模板信息.
   *
   * @param id 模板ID
   * @return 模板信息
   */
  @GetMapping("/{id}")
  public Result<ScrumTemplateVO> getById(@PathVariable Long id) {
    ScrumTemplate scrumTemplate = templateService.findById(id);
    return Result.success(templateConvert.entity2vo(scrumTemplate));
  }

  /**
   * 新增模板.
   *
   * @param dto 新增的模板信息
   * @return 新增后的模板信息
   */
  @PostMapping
  public Result<ScrumTemplateVO> save(@RequestBody @Validated(Create.class) ScrumTemplateDTO dto) {
    ScrumTemplate save = templateService.saveOrUpdate(templateConvert.dto2entity(dto));
    return Result.createSuccess(templateConvert.entity2vo(save));
  }

  /**
   * 更新模板.
   *
   * @param dto 更新的模板信息
   * @return 更新后的模板信息
   */
  @PutMapping
  public Result<ScrumTemplateVO> update(
      @RequestBody @Validated(Update.class) ScrumTemplateDTO dto) {
    ScrumTemplate update = templateService.saveOrUpdate(templateConvert.dto2entity(dto));
    return Result.updateSuccess(templateConvert.entity2vo(update));
  }

  /**
   * 更新模板状态.
   *
   * @param id 更新的模板ID
   * @param enabled 模板状态
   * @return 更新结果
   */
  @PostMapping("/enabled")
  public Result<Object> enabled(@RequestParam Long id, @RequestParam Boolean enabled) {
    templateManager.enabled(id, enabled);
    return Result.updateSuccess();
  }

  /**
   * 删除模板.
   *
   * @param id 删除的模板ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    templateManager.deleteById(id);
    return Result.deleteSuccess();
  }
}
