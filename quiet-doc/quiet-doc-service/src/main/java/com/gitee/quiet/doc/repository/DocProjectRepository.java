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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.jpa.repository.QuietRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocProjectRepository extends QuietRepository<DocProject> {

  /**
   * 根据分组ID查询所有项目信息
   *
   * @param groupId 项目分组ID
   * @return 在该分组下的所有项目信息
   */
  List<DocProject> findAllByGroupId(Long groupId);

  /**
   * 根据是否启 Swagger 用查询项目信息
   *
   * @param enabled 是否启用
   * @return 项目信息
   */
  List<DocProject> findAllBySwaggerEnabled(boolean enabled);
}
