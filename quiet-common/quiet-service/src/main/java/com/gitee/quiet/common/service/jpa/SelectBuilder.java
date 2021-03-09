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
public abstract class SelectBuilder {
    
    public static SelectBooleanBuilder booleanBuilder() {
        return new SelectBooleanBuilder();
    }
    
    /**
     * 获取查询条件
     *
     * @return 查询条件
     */
    public abstract Predicate getPredicate();
    
    public <T> QueryResults<T> from(@NotNull JPAQueryFactory jpaQueryFactory, @NotNull EntityPath<T> from,
            Pageable page) {
        JPAQuery<T> selectFrom = jpaQueryFactory.selectFrom(from).where(getPredicate());
        if (page != null) {
            selectFrom.offset(page.getOffset()).limit(page.getPageSize());
        }
        return selectFrom.fetchResults();
    }
}
