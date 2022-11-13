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

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocProject;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Project Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocProjectService {

  /**
   * 新增/更新文档项目
   *
   * @param entity 新增/更新的文档信息
   * @return 新增/更新后的文档信息
   */
  DocProject saveOrUpdate(@NotNull DocProject entity);

  /**
   * 根据ID删除文档项目
   *
   * @param id 文档项目ID
   */
  void delete(Long id);

  /**
   * 根据ID查询文档项目信息
   *
   * @param id 文档项目ID
   * @return 文档项目信息
   */
  DocProject getById(Long id);

  /**
   * 根据项目分组ID获取项目信息
   *
   * @param groupId 项目分组ID，小于等于0则查询创建人为当前登录人，且未分组的项目
   * @return 项目信息
   */
  List<DocProject> listAllByGroupId(Long groupId);

  /**
   * 根据项目名称、项目ID集合查询项目信息
   *
   * @param groupId 项目组ID
   * @param name    项目名称
   * @param ids     项目ID集合
   * @param limit   限制查询条数，小于等于0或者不传则查询 15 条信息
   * @return 项目信息
   */
  List<DocProject> list(Long groupId, String name, Set<Long> ids, Long limit);
}
