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

import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.jpa.entity.QDictionary;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

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
    
    public <T extends Number & Comparable<?>> SelectBooleanBuilder notNullEq(T param, NumberPath<T> path) {
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
    
    public SelectBooleanBuilder notNullEq(Dictionary type, QDictionary qDictionary) {
        if (ObjectUtils.allNotNull(type, type.getType(), type.getKey())) {
            builder.and(qDictionary.isType(type));
        }
        return this;
    }
}
