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
import com.gitee.quiet.scrum.vo.MyScrumProject;
import com.gitee.quiet.scrum.vo.ScrumProjectDetail;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 项目Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectService {

  /**
   * 获取用户的所有项目信息
   *
   * @param userId 用户ID
   * @return 项目信息
   */
  MyScrumProject allProjectByUserId(@NotNull Long userId);

  /**
   * 新增项目
   *
   * @param save 新增的项目信息
   * @return 新增后的项目信息
   */
  ScrumProject save(@NotNull ScrumProject save);

  /**
   * 根据项目ID查询项目信息
   *
   * @param ids 项目ID集合
   * @return 项目信息
   */
  List<ScrumProject> findAllByIds(Set<Long> ids);

  /**
   * 更新项目信息
   *
   * @param update 更新的项目信息
   * @return 更新后的项目信息
   */
  ScrumProject update(@NotNull ScrumProject update);

  /**
   * 根据项目ID删除项目信息
   *
   * @param id 要删除的项目的ID
   */
  void deleteById(@NotNull Long id);

  /**
   * 统计多少项目用了指定的模板
   *
   * @param templateId 统计的模板ID
   * @return 使用了该模板的项目数量
   */
  long countByTemplateId(@NotNull Long templateId);

  /**
   * 获取项目的详细信息，包含团队信息以及版本、迭代信息
   *
   * @param id 项目ID
   * @return 项目详细信息
   */
  ScrumProjectDetail getDetail(@NotNull Long id);

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
}
