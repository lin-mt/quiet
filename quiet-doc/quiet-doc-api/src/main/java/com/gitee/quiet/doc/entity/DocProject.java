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
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project")
public class DocProject extends SortableEntity {

  /** 访问者信息 */
  @Transient private final List<QuietUser> visitors = new ArrayList<>();

  /** 项目名称 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "project_name", nullable = false, length = 30)
  private String name;

  /** 接口基本路径 */
  @Length(max = 30)
  @Column(name = "base_path", length = 30)
  private String basePath;

  /** 项目文档负责人 */
  @NotNull
  @Column(name = "principal", nullable = false)
  private Long principal;

  /** 访问者用户ID */
  @Size(max = 30)
  @Column(name = "visitor_id", length = 570)
  private Set<Long> visitorIds;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  /** 负责人名称 */
  @Transient private String principalName;
}
