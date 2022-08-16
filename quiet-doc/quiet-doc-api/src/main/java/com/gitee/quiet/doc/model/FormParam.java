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

package com.gitee.quiet.doc.model;

import com.gitee.quiet.doc.enums.FormParamType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Form请求参数.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@EqualsAndHashCode
public class FormParam {

  /** 参数名称 */
  private String name;

  /** 是否必须 */
  private boolean required;

  /** 参数类型 */
  private FormParamType type;

  /** 最小长度 */
  private Long minLength;

  /** 最大长度 */
  private Long maxLength;

  /** ContentType */
  private String contentType;

  /** 参数示例 */
  private String example;

  /** 备注 */
  private String remark;
}
