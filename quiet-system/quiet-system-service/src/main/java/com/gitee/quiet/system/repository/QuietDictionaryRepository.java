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
import com.gitee.quiet.system.entity.QuietDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDictionaryRepository extends QuietRepository<QuietDictionary> {

  /**
   * 根据数据字典类型查询数据字典信息
   *
   * @param type 要查询的数据字典类型
   * @return 该类型的所有数据字典信息
   */
  List<QuietDictionary> findAllByType(String type);

  /**
   * 根据数据字典类型查询数据字典信息，不包含一级数据字典
   *
   * @param type 要查询的数据字典类型
   * @return 该类型下的所有数据字典信息
   */
  List<QuietDictionary> findAllByTypeAndKeyIsNotNullAndParentIdIsNotNull(String type);

  /**
   * 根据父数据字典ID查询子数据字典信息
   *
   * @param parentId 父数据字典ID
   * @return 所有子数据字典
   */
  List<QuietDictionary> findAllByParentId(Long parentId);

  /**
   * 根据数据字典类型和key查询数据字典
   *
   * @param type 数据字典类型
   * @param key 数据字典key
   * @return 数据字典信息
   */
  QuietDictionary findByTypeAndKey(String type, String key);
}
