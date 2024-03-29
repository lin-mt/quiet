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

import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.io.Serializable;

public final class AttributeConverterInterceptor {

  private AttributeConverterInterceptor() {}

  @RuntimeType
  public static <T extends Enum<T> & JpaCustomEnum<V>, V extends Serializable>
      V convertToDatabaseColumn(T attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @RuntimeType
  public static <T extends Enum<T> & JpaCustomEnum<V>, V extends Serializable>
      T convertToEntityAttribute(V dbData, @FieldValue("enumType") Class<T> enumType) {
    return dbData == null ? null : JpaCustomEnum.valueToEnum(enumType, dbData);
  }
}
