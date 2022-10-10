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

import com.gitee.quiet.scrum.entity.ScrumProject;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 项目Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectService {

  /**
   * 统计多少项目用了指定的模板
   *
   * @param templateId 统计的模板ID
   * @return 使用了该模板的项目数量
   */
  long countByTemplateId(@NotNull Long templateId);

  /**
   * 根据项目ID获取项目信息
   *
   * @param id 项目ID
   * @return 项目信息
   */
  ScrumProject findById(@NotNull Long id);

  /**
   * 根据项目ID获取项目信息
   *
   * @param id 项目ID
   * @return 项目信息
   */
  ScrumProject projectInfo(Long id);

  /**
   * 根据项目ID查询项目列表
   *
   * @param groupId 项目组ID
   * @return 项目列表
   */
  List<ScrumProject> list(Long groupId);

  /**
   * 根据项目组ID统计项目数量
   *
   * @param groupId 项目组ID
   * @return 项目数量
   */
  Long countByGroupId(Long groupId);
}
