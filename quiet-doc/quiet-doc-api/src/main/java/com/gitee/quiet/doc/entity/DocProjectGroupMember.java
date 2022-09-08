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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.doc.enums.Permission;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 项目组成员.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project_group_member")
public class DocProjectGroupMember extends BaseEntity {

  /** 分组ID */
  @NotNull
  @Column(name = "group_id", nullable = false)
  private Long groupId;

  /** 用户ID */
  @NotNull
  @Column(name = "user_id", nullable = false)
  private Long userId;

  /** 权限 */
  @NotNull
  @Column(name = "permission", nullable = false)
  private Permission permission;
}
