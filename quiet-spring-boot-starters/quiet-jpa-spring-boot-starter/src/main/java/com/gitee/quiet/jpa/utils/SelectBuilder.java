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

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

/**
 * 查询条件构造器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public abstract class SelectBuilder<T extends Predicate> {

  @NotNull
  public static SelectBooleanBuilder booleanBuilder() {
    return new SelectBooleanBuilder();
  }

  @NotNull
  public static SelectBooleanBuilder booleanBuilder(BaseEntity entity) {
    BooleanBuilder builder = null;
    if (entity != null) {
      builder = entity.booleanBuilder();
    }
    return new SelectBooleanBuilder(builder);
  }

  /**
   * 获取查询条件
   *
   * @return 查询条件
   */
  @NotNull
  public abstract T getPredicate();

  public <E> QueryResults<E> from(
      @NotNull JPAQueryFactory jpaQueryFactory, @NotNull EntityPath<E> from, Pageable page) {
    Predicate predicate = getPredicate();
    if (predicate == null) {
      throw new IllegalStateException("SelectBuilder 子类实现的方法不能返回 null");
    }
    JPAQuery<E> selectFrom = jpaQueryFactory.selectFrom(from).where(predicate);
    if (page != null) {
      selectFrom.offset(page.getOffset()).limit(page.getPageSize());
    }
    return selectFrom.fetchResults();
  }

  public <E> JPAQuery<E> from(
      @NotNull JPAQueryFactory jpaQueryFactory, @NotNull EntityPath<E> from) {
    Predicate predicate = getPredicate();
    if (predicate == null) {
      throw new IllegalStateException("SelectBuilder 子类实现的方法不能返回 null");
    }
    return jpaQueryFactory.selectFrom(from).where(predicate);
  }
}
