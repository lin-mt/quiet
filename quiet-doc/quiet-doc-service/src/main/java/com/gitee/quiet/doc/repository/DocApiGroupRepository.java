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

import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.jpa.repository.QuietRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Api 分组Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiGroupRepository extends QuietRepository<DocApiGroup> {

  /**
   * 根据项目ID和分组名称查询分组信息
   *
   * @param projectId 项目ID
   * @param name 分组名称
   * @return 分组信息
   */
  DocApiGroup findByProjectIdAndName(Long projectId, String name);

  /**
   * 根据项目ID查询所有接口分组
   *
   * @param projectId 项目ID
   * @return 项目所有接口分组
   */
  List<DocApiGroup> findAllByProjectId(Long projectId);

  /**
   * 根据项目Id和项目名称模糊查询指定数据的分组信息
   *
   * @param projectId 项目ID
   * @param name 项目名称
   * @param limit 条数
   * @return 分组信息
   */
  @Query(
      nativeQuery = true,
      value =
          "select * from doc_api_group "
              + "where project_id = :projectId and group_name like concat('%', :name, '%') limit :limit")
  List<DocApiGroup> findAllByProjectIdAndName(
      @Param("projectId") Long projectId, @Param("name") String name, @Param("limit") long limit);
}
