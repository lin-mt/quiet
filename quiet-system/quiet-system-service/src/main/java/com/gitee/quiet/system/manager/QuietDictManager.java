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

package com.gitee.quiet.system.manager;

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDict;
import com.gitee.quiet.system.entity.QuietDictType;
import com.gitee.quiet.system.repository.QuietDictRepository;
import com.gitee.quiet.system.service.QuietDictService;
import com.gitee.quiet.system.service.QuietDictTypeService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDictManager {

  private final QuietDictService dictService;

  private final QuietDictRepository dictRepository;

  private final QuietDictTypeService dictTypeService;

  /**
   * 新增/更新数据字典.
   *
   * @param entity 新增/更新的数据字典信息
   * @return 新增/更新后的数据字典信息
   */
  public QuietDict saveOrUpdate(@NotNull QuietDict entity) {
    dictTypeService.getById(entity.getTypeId());
    int keyLength = entity.getKey().length();
    if (keyLength % 2 != 0) {
      throw new ServiceException("dict.key.length.error");
    }
    QuietDict exist = dictService.getByTypeIdAndKey(entity.getTypeId(), entity.getKey());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException(
              "dict.typeId.key.exist", entity.getTypeId(), entity.getKey());
    }
    int maxLevel = keyLength / 2;
    int level = 1;
    while (level != maxLevel) {
      String levelKey = entity.getKey().substring(0, level * 2);
      if (!StringUtils.isNumeric(levelKey)) {
        throw new ServiceException("dict.key.must.numeric");
      }
      QuietDict parent = dictService.getByTypeIdAndKey(entity.getTypeId(), levelKey);
      if (parent == null) {
        throw new ServiceException("dict.level.not.exist", level, levelKey);
      }
      level += 1;
    }
    return dictRepository.saveAndFlush(entity);
  }

  /**
   * 根据是否启用和类型ID查询数据字典
   * @param enabled 是否启用，true：查询在已启用的数据字典类型下已启用的数据字典，false：查询未启用的数据字典
   * @param typeId 数据字典类型
   * @return 数据字典信息
   */
  public List<QuietDict> findByEnabledAndTypeId(Boolean enabled, Long typeId) {
    if (Objects.isNull(typeId)) {
      return List.of();
    }
    QuietDictType dictType = dictTypeService.getById(typeId);
    if (Objects.nonNull(enabled) && enabled) {
      // 查询启用的数据字典，如果数据字典类型未启用，则返回空
      if (!dictType.getEnabled()) {
        return List.of();
      }
    }
    return dictRepository.findAllByEnabledAndTypeId(enabled, typeId);
  }
}
