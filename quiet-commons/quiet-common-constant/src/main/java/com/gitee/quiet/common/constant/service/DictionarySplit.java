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

package com.gitee.quiet.common.constant.service;

public final class DictionarySplit {

  /** 分隔符 */
  public static final String SEPARATOR = ".";

  /** 拆分字符串的正则表达式，字符串的格式为：${type}.${key}.${value} */
  public static final String REGEX = "\\.";

  /** 分割后至少包含 type 和 key */
  public static final Integer ARRAY_MIN_LENGTH = 2;

  private DictionarySplit() {}
}
