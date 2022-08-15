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

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.gitee.quiet.system.entity.QQuietRoute.quietRoute;

/**
 * 路由信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_route")
public class QuietRoute extends BaseEntity {

  /** 网关的路由ID */
  @NotBlank
  @Length(max = 60)
  @Column(name = "route_id", length = 60, nullable = false)
  private String routeId;

  /** 环境，用于批量修改发布 */
  @NotNull
  @Column(name = "environment", length = 30, nullable = false)
  private Dictionary<?> environment;

  /** 路由目标 */
  @NotBlank
  @Length(max = 200)
  @Column(name = "uri", length = 200, nullable = false)
  private String uri;

  /** 排序 */
  @Column(name = "route_order", nullable = false)
  private int order;

  /** 匹配规则 */
  @Column(name = "predicates", length = 1000)
  private Set<String> predicates;

  /** 过滤 */
  @Column(name = "filters", length = 1000)
  private Set<String> filters;

  @Length(max = 300)
  @Column(name = "remark", length = 300)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    // @formatter:off
    return SelectBuilder.booleanBuilder()
        .notBlankContains(getRouteId(), quietRoute.routeId)
        .notNullEq(getEnvironment(), quietRoute.environment)
        .notBlankContains(getUri(), quietRoute.uri)
        .notBlankContains(getRemark(), quietRoute.remark)
        .getPredicate();
    // @formatter:on
  }

  public void addPredicate(String predicate) {
    if (getPredicates() == null) {
      setPredicates(new HashSet<>());
    }
    getPredicates().add(predicate);
  }

  public void removePredicate(String predicate) {
    if (getPredicates() != null) {
      getPredicates().remove(predicate);
    }
  }

  public void addFilter(String filter) {
    if (getFilters() == null) {
      setFilters(new HashSet<>());
    }
    getFilters().add(filter);
  }

  public void removeFilter(String filter) {
    if (getFilters() != null) {
      getFilters().remove(filter);
    }
  }
}
