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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietDept;
import com.gitee.quiet.system.entity.QuietUser;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 部门Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDeptService {

  /**
   * 分页查询部门数据
   *
   * @param params 查询条件
   * @param page 分页参数
   * @return 查询结果
   */
  Page<QuietDept> page(QuietDept params, Pageable page);

  /**
   * 保存或者更新部门数据
   *
   * @param dept 保存或者更新的部门ID
   */
  QuietDept saveOrUpdate(QuietDept dept);

  /**
   * 删除部门数据
   *
   * @param deleteId 要删除的部门ID
   */
  void deleteById(Long deleteId);

  /**
   * 获取所有部门的树形结构信息
   *
   * @return 所有部门信息
   */
  List<QuietDept> tree();

  /**
   * 分页查询部门下的用户信息
   *
   * @param deptId 部门ID
   * @param params 用户过滤条件
   * @param page 分页信息
   * @return 部门下的用户信息
   */
  QueryResults<QuietUser> pageUser(Long deptId, QuietUser params, Pageable page);
}
