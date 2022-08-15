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

import com.gitee.quiet.system.entity.QuietDictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 数据字典Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDictionaryService {

  /**
   * 根据数据字典类型返回该类型的树形结构
   *
   * @param type 数据字典类型
   * @return type的树形结构
   */
  List<QuietDictionary> treeByType(String type);

  /**
   * 分页查询数据字典.
   *
   * @param params 查询参数
   * @param page 分页参数
   * @return 查询的所有信息
   */
  Page<QuietDictionary> page(QuietDictionary params, Pageable page);

  /**
   * 新增数据字典.
   *
   * @param save 新增的数据字典信息
   * @return 新增后的数据字典信息
   */
  QuietDictionary save(QuietDictionary save);

  /**
   * 根据数据字典ID删除数据
   *
   * @param id 要删除的数据字典ID
   * @return 删除的字典信息
   */
  QuietDictionary delete(Long id);

  /**
   * 更新数据字典
   *
   * @param update 要更新的数据字典
   * @return 更新后的数据字典信息
   */
  QuietDictionary update(QuietDictionary update);

  /**
   * 查询数据字典提供选项
   *
   * @param type 数据字典类型
   * @return 该数据字典类型下的选项
   */
  List<QuietDictionary> listByTypeForSelect(String type);
}
