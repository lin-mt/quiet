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

import com.gitee.quiet.scrum.entity.ScrumPriority;

import java.util.List;

/**
 * 优先级信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumPriorityService {

  /**
   * 根据模板ID删除优先级信息
   *
   * @param templateId 模板ID
   */
  void deleteByTemplateId(Long templateId);

  /**
   * 根据模板ID查询优先级配置信息
   *
   * @param templateId 模板ID
   * @return 模板ID下的所有优先级配置信息
   */
  List<ScrumPriority> list(Long templateId);

  /**
   * 根据模板ID统计优先级数量
   *
   * @param templateId 模板ID
   * @return 优先级数量
   */
  long countByTemplateId(Long templateId);
}
