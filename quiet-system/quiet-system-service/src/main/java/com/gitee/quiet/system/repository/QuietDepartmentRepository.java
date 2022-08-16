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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDepartmentRepository extends QuietRepository<QuietDepartment> {

  /**
   * 根据部门名称查询部门信息
   *
   * @param departmentName 部门名称
   * @return 部门信息
   */
  QuietDepartment getByDepartmentName(String departmentName);

  /**
   * 根据部门 ID 查询子部门信息
   *
   * @param parentId 父级部门ID
   * @return 所有子部门信息
   */
  List<QuietDepartment> findAllByParentId(Long parentId);
}
