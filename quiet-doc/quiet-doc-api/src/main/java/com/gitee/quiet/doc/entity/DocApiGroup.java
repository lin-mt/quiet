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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.jpa.entity.SortableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Api 分组信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_group")
public class DocApiGroup extends SortableEntity {

  /** 分组名称 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "group_name", length = 30, nullable = false)
  private String name;

  /** 所属项目ID */
  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  /** 备注 */
  @Length(max = 300)
  @Column(name = "remark", length = 300)
  private String remark;
}
