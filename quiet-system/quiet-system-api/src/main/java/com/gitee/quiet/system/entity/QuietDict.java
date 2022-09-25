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

import com.gitee.quiet.jpa.entity.Dict;
import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.gitee.quiet.system.entity.QQuietDict.quietDict;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_dict")
public class QuietDict extends Dict {

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBooleanBuilder.booleanBuilder()
        .isIdEq(getId(), quietDict.id)
        .isIdEq(getTypeId(), quietDict.typeId)
        .notBlankEq(getKey(), quietDict.key)
        .notBlankContains(getName(), quietDict.name)
        .notNullEq(getEnabled(), quietDict.enabled)
        .getPredicate();
  }
}
