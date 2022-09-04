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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietDict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDictRepository extends QuietRepository<QuietDict> {

  /**
   * 根据数据字典类型查询所有数据字典
   *
   * @param typeId 数据字典类型
   * @return 数据字典信息
   */
  List<QuietDict> findAllByTypeId(Long typeId);

  /**
   * 根据数据字典类型ID和key查询数据字典
   *
   * @param typeId 数据字典类型ID
   * @param key 字典key
   * @return 数据字典信息
   */
  QuietDict findByTypeIdAndKey(Long typeId, String key);

  /**
   * 根据数据字典类型ID和数据字典key查询数据字典的子数据字典
   *
   * @param typeId 数据字典类型ID
   * @param key 数据字典key
   * @return 数据字典的子数据字典
   */
  List<QuietDict> findByTypeIdAndKeyIsStartingWith(Long typeId, String key);

  /**
   * 根据是否启用和数据字典类型ID查询数据字典信息
   *
   * @param enabled 是否启用
   * @param typeId 数据字典类型
   * @return 数据字典信息
   */
  List<QuietDict> findAllByEnabledAndTypeId(Boolean enabled, Long typeId);
}
