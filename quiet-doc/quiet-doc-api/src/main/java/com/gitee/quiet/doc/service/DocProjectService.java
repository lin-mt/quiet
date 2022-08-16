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
import com.gitee.quiet.doc.vo.MyDocProject;

/**
 * Project Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocProjectService {

  /**
   * 根据用户ID查询用户的文档项目信息
   *
   * @param userId 用户ID
   * @return 可访问的项目信息
   */
  MyDocProject getProjectByUserId(Long userId);

  /**
   * 新建文档项目
   *
   * @param save 新增的文档信息
   * @return 新增的文档信息
   */
  DocProject save(DocProject save);

  /**
   * 更新文档项目
   *
   * @param update 更新的文档信息
   * @return 更新后的文档信息
   */
  DocProject update(DocProject update);

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
}
