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

import com.gitee.quiet.doc.converter.DocProjectEnvConverter;
import com.gitee.quiet.doc.dto.DocProjectEnvDTO;
import com.gitee.quiet.doc.entity.DocProjectEnv;
import com.gitee.quiet.doc.service.DocProjectEnvService;
import com.gitee.quiet.doc.vo.DocProjectEnvVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目环境接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project-env")
public class DocProjectEnvController {

  private final DocProjectEnvConverter converter;
  private final DocProjectEnvService service;

  /**
   * 创建项目环境
   *
   * @param dto 环境信息
   * @return 创建的环境信息
   */
  @PostMapping
  public Result<DocProjectEnvVO> save(@RequestBody @Validated(Create.class) DocProjectEnvDTO dto) {
    DocProjectEnv projectEnv = service.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(projectEnv));
  }

  /**
   * 更新项目环境配置
   *
   * @param dto 环境配置信息
   * @return 更新的环境信息
   */
  @PutMapping
  public Result<DocProjectEnvVO> update(@RequestBody @Validated(Update.class)  DocProjectEnvDTO dto) {
    DocProjectEnv update = service.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }

  /**
   * 根据ID删除环境配置
   *
   * @param id 环境配置ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    service.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 根据项目ID查询所有环境配置信息
   *
   * @param projectId 项目ID
   * @return 项目环境配置信息
   */
  @GetMapping("/list/{projectId}")
  public Result<List<DocProjectEnvVO>> listByProjectId(@PathVariable Long projectId) {
    List<DocProjectEnv> docProjectEnvs = service.listByProjectId(projectId);
    return Result.success(converter.entities2vos(docProjectEnvs));
  }
}
