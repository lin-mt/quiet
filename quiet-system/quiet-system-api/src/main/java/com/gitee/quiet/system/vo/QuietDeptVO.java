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

package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.ParentVO;
import com.gitee.quiet.service.vo.front.TreeSelectVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 部门信息 Vo.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDeptVO extends ParentVO<QuietDeptVO>
    implements TreeSelectVO<Long, QuietDeptVO> {

  /** 部门名称 */
  @NotBlank
  @Length(max = 10)
  private String deptName;

  /** 备注 */
  @Length(max = 100)
  private String remark;

  @Override
  public String getTitle() {
    return getDeptName();
  }

  @Override
  public Long getValue() {
    return getId();
  }
}
