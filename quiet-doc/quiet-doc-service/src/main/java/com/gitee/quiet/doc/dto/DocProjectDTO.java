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

package com.gitee.quiet.doc.dto;

import com.gitee.quiet.service.dto.SortableDTO;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * 项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocProjectDTO extends SortableDTO {

  /** 项目名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 接口基本路径 */
  @Length(max = 30)
  private String basePath;

  /** 分组ID */
  private Long groupId;

  /** 项目文档负责人 */
  @NotNull private Long principal;

  /** 成员用户ID */
  @Size(max = 30)
  private Set<Long> memberIds;

  /** 备注 */
  @Length(max = 100)
  private String remark;

  /** 访问者信息 */
  @Transient private List<QuietUser> visitors;
}
