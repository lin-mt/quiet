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

package com.gitee.quiet.doc.enums;

import com.gitee.quiet.jpa.enums.base.IntegerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum Permission implements IntegerEnum {

  /** 组长 */
  GROUP_LEADER(0),
  /** 开发者 */
  DEVELOPER(1),
  /** 测试人员 */
  TESTER(2),
  /** 访客 */
  VISITOR(3);

  private final Integer value;
}
