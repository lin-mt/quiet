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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目版本repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumVersionRepository extends QuietRepository<ScrumVersion> {

  /**
   * 根据项目id删除该项目下的所有版本信息
   *
   * @param projectId 项目ID
   */
  void deleteByProjectId(Long projectId);

  /**
   * 根据项目ID查询项目版本信息
   *
   * @param projectId 项目ID
   * @return 项目所有版本信息
   */
  List<ScrumVersion> findAllByProjectId(Long projectId);

  /**
   * 根据项目ID和项目名称查询版本信息
   *
   * @param projectId 项目ID
   * @param name 项目名称
   * @return 版本信息
   */
  ScrumVersion findByProjectIdAndName(Long projectId, String name);

  /**
   * 根据父版本ID统计该父版本下有多少子版本
   *
   * @param parentId 父版本ID
   * @return 子版本数量
   */
  long countByParentId(Long parentId);

  /**
   * 根据版本ID查询所有子版本
   *
   * @param id 版本ID
   * @return 子版本信息
   */
  List<ScrumVersion> findAllByParentId(Long id);
}
