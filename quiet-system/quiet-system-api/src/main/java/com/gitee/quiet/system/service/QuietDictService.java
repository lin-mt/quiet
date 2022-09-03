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

import com.gitee.quiet.system.entity.QuietDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDictService {

  /**
   * 根据数据字典类型查询所有数据字典
   *
   * @param typeId 数据字典类型Id
   * @return 数据字典信息
   */
  List<QuietDict> listByTypeId(Long typeId);

  /**
   * 分页查询数据字典.
   *
   * @param entity 查询参数
   * @param page 分页参数
   * @return 查询所有信息
   */
  Page<QuietDict> page(QuietDict entity, Pageable page);

  /**
   * 根据数据字典ID删除数据字典
   *
   * @param id 数据字典ID
   */
  void deleteById(Long id);

  /**
   * 根据ID查询数据字典
   *
   * @param id 数据字典ID
   * @return 数据字典信息
   */
  QuietDict getById(Long id);

  /**
   * 根据数据字典类型ID和key查询数据字典
   *
   * @param typeId 数据字典类型ID
   * @param key 字典key
   * @return 数据字典信息
   */
  QuietDict getByTypeIdAndKey(Long typeId, String key);

}
