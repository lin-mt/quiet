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

package com.gitee.quiet.jpa.enums.base;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

interface JpaCustomEnum<T extends Serializable> {

  /** 数据库字段至枚举的转换工具。 */
  static <V extends Serializable, E extends Enum<E> & JpaCustomEnum<V>> E valueToEnum(
      Class<E> enumType, V value) {
    return Stream.of(enumType.getEnumConstants())
        .filter(item -> Objects.equals(item.getValue(), value))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Unknown enum value: " + value + " for type: " + enumType.getSimpleName()));
  }

  /**
   * 存储数据库的值
   *
   * @return 数据库值
   */
  T getValue();
}
