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

package com.gitee.quiet.common.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * String 风格转换工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class StringConverterUtil {

  private static final Converter<String, String> UNDERSCORE_TO_CAMEL_CONVERTER =
      CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);

  private static final Converter<String, String> CAMEL_TO_UNDERSCORE_CONVERTER =
      CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

  private StringConverterUtil() {}

  /**
   * 转为驼峰风格的字符串
   *
   * @param target 要转换的字符串
   * @return 驼峰风格的字符串
   */
  public static String lowerCamel(String target) {
    if (StringUtils.isNotBlank(target)) {
      return UNDERSCORE_TO_CAMEL_CONVERTER.convert(target);
    }
    return target;
  }

  /**
   * 转为下划线风格的字符串
   *
   * @param target 要转换的字符串
   * @return 下划线风格的字符串
   */
  public static String lowerUnderscore(String target) {
    if (StringUtils.isNotBlank(target)) {
      return CAMEL_TO_UNDERSCORE_CONVERTER.convert(target);
    }
    return target;
  }
}
