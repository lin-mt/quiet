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

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 项目配置信息 VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocProjectConfigVO extends BaseVO {

  /** 配置名称 */
  @NotEmpty
  @Length(max = 30)
  private String name;

  /** 请求路径 */
  @Length(max = 90)
  private String baseUrl;

  /** 项目ID */
  @NotNull private Long projectId;

  /** 备注 */
  @Length(max = 100)
  private String remark;
}
