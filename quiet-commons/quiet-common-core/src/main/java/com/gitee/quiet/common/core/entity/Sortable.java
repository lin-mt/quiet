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

package com.gitee.quiet.common.core.entity;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 可排序.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Sortable extends Comparable<Sortable> {

  /**
   * 获取排序的序号
   *
   * @return 序号
   */
  int getSortNum();

  /**
   * 设置排序的序号
   *
   * @param sortNum 序号
   */
  void setSortNum(int sortNum);

  /**
   * 跟其他对象进行比较
   *
   * @param other 比较的对象
   * @return 比较结果
   */
  @Override
  default int compareTo(@Nullable Sortable other) {
    if (other == null) {
      return 1;
    }
    return Integer.compare(getSortNum(), other.getSortNum());
  }

  class Utils {

    public static <T> List<T> sortSerial(List<T> value) {
      if (CollectionUtils.isNotEmpty(value)) {
        List<T> sorted = new ArrayList<>(value);
        Map<Integer, T> indexToValue = new HashMap<>(sorted.size());
        for (int i = 0; i < sorted.size(); i++) {
          T t = sorted.get(i);
          if (t instanceof Sortable) {
            indexToValue.put(i, t);
          }
        }
        if (MapUtils.isNotEmpty(indexToValue)) {
          List<T> sort = indexToValue.values().stream().sorted().collect(Collectors.toList());
          int index = 0;
          for (Map.Entry<Integer, T> entry : indexToValue.entrySet()) {
            sorted.set(entry.getKey(), sort.get(index));
            index++;
          }
        }
        return sorted;
      } else {
        return value;
      }
    }
  }
}
