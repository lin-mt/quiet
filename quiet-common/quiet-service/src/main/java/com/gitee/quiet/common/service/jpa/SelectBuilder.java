/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.common.service.jpa;

import com.gitee.quiet.common.service.base.BaseEntity;
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
    
    public <E> QueryResults<E> from(@NotNull JPAQueryFactory jpaQueryFactory, @NotNull EntityPath<E> from,
            Pageable page) {
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
}
