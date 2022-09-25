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

import com.gitee.quiet.doc.enums.ApiState;
import com.gitee.quiet.doc.enums.HttpMethod;
import com.gitee.quiet.service.vo.SortableVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 文档信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiVO extends SortableVO {

  /** 接口名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 项目ID */
  @NotNull private Long projectId;

  /** 接口状态 */
  @NotNull private ApiState apiState;

  /** 请求地址 */
  @NotBlank
  @Length(max = 300)
  private String path;

  /** 请求方法 */
  @NotNull private HttpMethod method;

  /** 作者ID */
  private Long authorId;

  /** 作者全名 */
  private String authorFullName;

  /** 所属分组ID */
  private Long apiGroupId;

  /** 访问者用户ID */
  @Size(max = 30)
  private Set<Long> visitorIds;

  /** 备注 */
  @Length(max = 300)
  private String remark;

  /** 创建人用户名 */
  private String creatorFullName;

  /** 更新人用户名 */
  private String updaterFullName;

  /** 所属分组信息 */
  private DocApiGroupVO apiGroup;

  /** api 详细信息 */
  private DocApiInfoVO apiInfo;
}
