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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.service.dto.ParentDTO;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 部门信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDeptDTO extends ParentDTO<QuietDeptDTO> {

  /** 部门名称 */
  private String deptName;

  /** 备注 */
  private String remark;

  /** 用户信息 */
  private QuietUser params;

  /** 用户ID集合 */
  private Set<Long> userIds;
}
