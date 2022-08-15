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

package com.gitee.quiet.jpa.utils;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.entity.QDictionary;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * 构建 BooleanBuilder.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class SelectBooleanBuilder extends SelectBuilder<BooleanBuilder> {

  private final BooleanBuilder builder;

  public SelectBooleanBuilder() {
    this.builder = new BooleanBuilder();
  }

  public SelectBooleanBuilder(BooleanBuilder builder) {
    this.builder = builder == null ? new BooleanBuilder() : builder;
  }

  @Override
  public BooleanBuilder getPredicate() {
    return builder;
  }

  public SelectBooleanBuilder and(@Nullable Predicate right) {
    builder.and(right);
    return this;
  }

  public SelectBooleanBuilder andAnyOf(Predicate... args) {
    builder.andAnyOf(args);
    return this;
  }

  public SelectBooleanBuilder andNot(Predicate right) {
    return and(right.not());
  }

  public SelectBooleanBuilder or(@Nullable Predicate right) {
    builder.or(right);
    return this;
  }

  public SelectBooleanBuilder orAllOf(Predicate... args) {
    builder.orAllOf(args);
    return this;
  }

  public SelectBooleanBuilder orNot(Predicate right) {
    return or(right.not());
  }

  public SelectBooleanBuilder notNullEq(Boolean param, BooleanPath path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public <T extends Number & Comparable<?>> SelectBooleanBuilder notNullEq(
      T param, NumberPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public SelectBooleanBuilder notBlankEq(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public SelectBooleanBuilder with(@NotNull Consumer<SelectBooleanBuilder> consumer) {
    if (consumer != null) {
      consumer.accept(this);
    }
    return this;
  }

  public <T extends Enum<T>> SelectBooleanBuilder notNullEq(T param, EnumPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public SelectBooleanBuilder notBlankContains(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.contains(param));
    }
    return this;
  }

  public SelectBooleanBuilder notNullEq(Dictionary<?> dictionary, QDictionary qDictionary) {
    if (dictionary != null
        && StringUtils.isNoneBlank(dictionary.getType())
        && StringUtils.isNoneBlank(dictionary.getKey())) {
      builder.and(qDictionary.eq(dictionary));
    }
    return this;
  }

  public SelectBooleanBuilder notNullBefore(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      path.before(param);
    }
    return this;
  }

  public SelectBooleanBuilder notNullAfter(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      path.after(param);
    }
    return this;
  }

  public SelectBooleanBuilder notEmptyIn(Collection<? extends Long> param, NumberPath<Long> path) {
    if (CollectionUtils.isNotEmpty(param)) {
      path.in(param);
    }
    return this;
  }
}
