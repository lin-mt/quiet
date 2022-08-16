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

package com.gitee.quiet.jpa.converter;

import com.gitee.quiet.common.constant.Delimiter;
import com.google.common.base.Joiner;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Set<Long> 与 String 的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class SetLongStringConverter implements AttributeConverter<Set<Long>, String> {

  private static final String DELIMITER = Delimiter.COMMA;

  @Override
  public String convertToDatabaseColumn(Set<Long> attribute) {
    if (CollectionUtils.isEmpty(attribute)) {
      return null;
    }
    return Joiner.on(DELIMITER).join(attribute);
  }

  @Override
  public Set<Long> convertToEntityAttribute(String dbData) {
    Set<Long> attribute = new HashSet<>();
    if (StringUtils.isBlank(dbData)) {
      return attribute;
    }
    StringTokenizer st = new StringTokenizer(dbData, DELIMITER);
    while (st.hasMoreTokens()) {
      attribute.add(Long.parseLong(st.nextToken()));
    }
    return attribute;
  }
}
