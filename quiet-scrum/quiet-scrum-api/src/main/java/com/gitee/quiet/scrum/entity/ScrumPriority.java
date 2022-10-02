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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.jpa.entity.SortableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 优先级.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_priority")
public class ScrumPriority extends SortableEntity {

  /** 优先级名称 */
  @NotBlank
  @Length(max = 10)
  @Column(name = "priority_name", nullable = false, length = 10)
  private String name;

  /** 图标的十六进制颜色 */
  @Length(max = 7)
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
  @Column(name = "color_hex", length = 7, nullable = false)
  private String colorHex;

  /** 模板ID */
  @NotNull
  @Column(name = "template_id", nullable = false)
  private Long templateId;

  /** 备注信息 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;
}
