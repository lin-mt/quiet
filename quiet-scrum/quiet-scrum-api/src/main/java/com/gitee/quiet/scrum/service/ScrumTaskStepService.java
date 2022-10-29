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

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 任务步骤service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTaskStepService {

  /**
   * 根据模板ID查询任务步骤信息
   *
   * @param templateId 模板ID
   * @return 模板下的任务步骤
   */
  List<ScrumTaskStep> list(@NotNull Long templateId);

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
   * 根据任务步骤ID获取任务步骤信息
   *
   * @param id 任务步骤ID
   * @return 任务步骤信息
   */
  ScrumTaskStep getById(Long id);

}
