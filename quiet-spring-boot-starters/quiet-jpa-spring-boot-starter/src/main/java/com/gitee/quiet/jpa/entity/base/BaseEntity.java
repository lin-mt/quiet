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

package com.gitee.quiet.jpa.entity.base;

import com.gitee.quiet.jpa.listener.EntityLoggingListener;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.BooleanBuilder;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类的公共属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@TypeDefs({@TypeDef(name = "json", typeClass = JsonType.class)})
@EntityListeners({AuditingEntityListener.class, EntityLoggingListener.class})
public class BaseEntity implements Serializable {

  @Id
  @Null(groups = Create.class)
  @NotNull(groups = Update.class)
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
  @GenericGenerator(name = "IdGenerator", strategy = "com.gitee.quiet.jpa.id.IdGenerator")
  private Long id;

  @CreatedBy
  @Column(name = "creator", updatable = false)
  private Long creator;

  @LastModifiedBy
  @Column(name = "updater", insertable = false)
  private Long updater;

  @CreatedDate
  @Column(name = "gmt_create", updatable = false)
  private LocalDateTime gmtCreate;

  @LastModifiedDate
  @Column(name = "gmt_update", insertable = false)
  private LocalDateTime gmtUpdate;

  @Nullable
  public BooleanBuilder booleanBuilder() {
    return null;
  }
}
