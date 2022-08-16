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

package com.gitee.quiet.jpa.utils;

import com.gitee.quiet.common.core.entity.Parent;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体类工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class EntityUtils {

  private EntityUtils() {}

  /**
   * 构建树形结构的数据
   *
   * @param source 要构建的原数据
   * @param <T> 实体
   * @return 构建后的数据
   */
  public static <T extends BaseEntity & Parent<T>> List<T> buildTreeData(List<T> source) {
    List<T> treeData = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(source)) {
      Map<Long, T> idToEntity =
          source.stream().collect(Collectors.toMap(BaseEntity::getId, p -> p));
      Set<Long> keys = idToEntity.keySet();
      for (T datum : source) {
        if (datum.getParentId() == null || !keys.contains(datum.getParentId())) {
          treeData.add(datum);
          continue;
        }
        idToEntity.get(datum.getParentId()).addChildren(datum);
      }
    }
    return treeData;
  }
}
