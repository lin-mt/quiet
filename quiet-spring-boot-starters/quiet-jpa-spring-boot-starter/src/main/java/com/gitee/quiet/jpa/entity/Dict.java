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

package com.gitee.quiet.jpa.entity;

import com.gitee.quiet.jpa.entity.base.BaseDict;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@QueryEntity
@MappedSuperclass
public class Dict extends SortableEntity implements BaseDict {

  /** 字典类型ID */
  @Column(name = "type_id", nullable = false)
  private Long typeId;

  /** 字典key，格式为每层级占两位数字，第一层级范围：00-99，第二层级的前两位为第一层级的key， 所以第二层级范围为：0000-9999，后续层级以此类推 */
  @Length(max = 18)
  @Column(name = "dict_key", length = 18, nullable = false)
  private String key;

  /** 名称 */
  @Length(max = 10)
  @Column(name = "dict_name", length = 10, nullable = false)
  private String name;

  /** 是否启用 */
  @ColumnDefault("0")
  @Column(name = "enabled", columnDefinition = "TINYINT(1)", nullable = false)
  private Boolean enabled;
}
