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

package com.gitee.quiet.scrum.filter;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.scrum.dictionary.DemandType;
import lombok.Getter;
import lombok.Setter;

/**
 * 需求过滤条件.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumDemandFilter {

  /** 是否已规划 */
  private Boolean planned;

  /** 优先级ID */
  private Long priorityId;

  /** 需求类型 */
  private Dictionary<DemandType> demandType;
}
