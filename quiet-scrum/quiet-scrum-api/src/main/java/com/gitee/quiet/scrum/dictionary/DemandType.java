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

package com.gitee.quiet.scrum.dictionary;

import com.gitee.quiet.jpa.entity.Dictionary;

/**
 * 需求类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DemandType extends Dictionary<DemandType> {

  /** 功能需求 */
  public static final DemandType Function = new DemandType("Function");

  /** 优化需求 */
  public static final DemandType Optimize = new DemandType("Optimize");

  private DemandType(String key) {
    super(DemandType.class.getSimpleName(), key);
  }
}
