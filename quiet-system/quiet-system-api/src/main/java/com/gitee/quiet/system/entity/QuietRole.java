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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.security.entity.QuietGrantedAuthority;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import static com.gitee.quiet.system.entity.QQuietRole.quietRole;

/**
 * 角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_role")
public class QuietRole extends QuietGrantedAuthority<QuietRole> {

  /** 角色中文名 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "role_cn_name", nullable = false, length = 30)
  private String roleCnName;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  /** 父角色名称 */
  @Transient private String parentRoleName;

  @Override
  @Transient
  public String getAuthority() {
    return getRoleName();
  }

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietRole.id)
        .isIdEq(getParentId(), quietRole.parentId)
        .notBlankContains(getRoleName(), quietRole.roleName)
        .notBlankContains(getRoleCnName(), quietRole.roleCnName)
        .notBlankContains(getRemark(), quietRole.remark)
        .getPredicate();
  }
}
