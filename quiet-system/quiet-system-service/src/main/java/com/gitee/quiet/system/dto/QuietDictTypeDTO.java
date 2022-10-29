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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.service.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDictTypeDTO extends BaseDTO {

  /** 服务ID */
  @NotBlank
  @Length(max = 30)
  private String serviceId;

  /** key */
  @NotBlank
  @Length(max = 30)
  private String key;

  /** 名称 */
  @NotBlank
  @Length(max = 10)
  private String name;

  /** 是否启用 */
  @NotNull private Boolean enabled;

  /** 备注 */
  private String remark;
}
