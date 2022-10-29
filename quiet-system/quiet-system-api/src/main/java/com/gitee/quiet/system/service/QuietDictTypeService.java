/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietDictType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDictTypeService {

  /**
   * 分页查询数据字典类型.
   *
   * @return 查询所有信息
   */
  Page<QuietDictType> page(QuietDictType entity, Pageable page);

  /**
   * 新增/更新数据字典类型.
   *
   * @param entity 新增/更新的数据字典类型信息
   * @return 新增/更新后的数据字典类型信息
   */
  QuietDictType saveOrUpdate(QuietDictType entity);

  /**
   * 根据ID查询数据字典类型信息
   *
   * @param id 数据字典类型ID
   */
  QuietDictType getById(Long id);

  /**
   * 根据启用状态查询所有数据字典类型.
   *
   * @param enabled 启用状态
   * @param name 数据字典类型名称
   * @return 数据字典类型
   */
  List<QuietDictType> listByEnabledAndName(boolean enabled, String name);

  /**
   * 根据ID集合批量查询数据字典类型
   *
   * @param ids ID集合
   * @return 数据字典类型信息
   */
  List<QuietDictType> listByIds(Set<Long> ids);
}
