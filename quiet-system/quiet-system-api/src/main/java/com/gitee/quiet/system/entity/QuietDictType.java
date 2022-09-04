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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.gitee.quiet.system.entity.QQuietDictType.quietDictType;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_dict_type")
public class QuietDictType extends BaseEntity {

  /** 服务ID */
  @Length(max = 30)
  @Column(name = "service_id", length = 30)
  private String serviceId;

  /** 名称 */
  @Length(max = 10)
  @Column(name = "dict_type_name", length = 10)
  private String name;

  /** 是否启用 */
  @ColumnDefault("0")
  @Column(name = "enabled", columnDefinition = "TINYINT(1)")
  private Boolean enabled;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBooleanBuilder.booleanBuilder()
        .notNullEq(getId(), quietDictType.id)
        .notBlankContains(getServiceId(), quietDictType.serviceId)
        .notBlankContains(getName(), quietDictType.name)
        .notNullEq(getEnabled(), quietDictType.enabled)
        .getPredicate();
  }
}
