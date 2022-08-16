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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumTaskStep;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 任务步骤service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTaskStepService {

  /**
   * 新增任务步骤信息
   *
   * @param save 新增任务步骤
   * @return 新增任务步骤后的信息
   */
  ScrumTaskStep save(@NotNull ScrumTaskStep save);

  /**
   * 更新任务步骤信息
   *
   * @param update 更新的任务步骤信息
   * @return 更新后的任务步骤信息
   */
  ScrumTaskStep update(@NotNull ScrumTaskStep update);

  /**
   * 根据模板ID查询任务步骤信息
   *
   * @param templateId 模板ID
   * @return 模板下的任务步骤
   */
  List<ScrumTaskStep> findAllByTemplateId(@NotNull Long templateId);

  /**
   * 根据模板ID集合查询所有任务步骤信息
   *
   * @param templateIds 模板ID集合
   * @return 模板下的所有任务步骤
   */
  Map<Long, List<ScrumTaskStep>> findAllByTemplateIds(@NotNull @NotEmpty Set<Long> templateIds);

  /**
   * 删除任务步骤信息
   *
   * @param id 任务步骤ID
   */
  void deleteById(@NotNull Long id);

  /**
   * 批量更新任务步骤信息
   *
   * @param taskSteps 任务步骤信息
   */
  List<ScrumTaskStep> updateBatch(List<ScrumTaskStep> taskSteps);

  /**
   * 根据模板ID删除任务步骤配置信息
   *
   * @param templateId 模板ID
   */
  void deleteByTemplateId(Long templateId);

  /**
   * 根据模板ID统计任务步骤数量
   *
   * @param templateId 模板ID
   * @return 任务步骤数
   */
  long countByTemplateId(Long templateId);

  /**
   * 校验是否存在该任务步骤ID
   *
   * @param id 任务步骤ID
   */
  void checkIdExist(Long id);
}
