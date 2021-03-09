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

package com.gitee.quiet.common.service.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;

/**
 * 构建 BooleanBuilder.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class WhereBooleanBuilder {
    
    private final BooleanBuilder builder;
    
    private WhereBooleanBuilder() {
        builder = new BooleanBuilder();
    }
    
    public static WhereBooleanBuilder create() {
        return new WhereBooleanBuilder();
    }
    
    public BooleanBuilder getBuilder() {
        return builder;
    }
    
    public <T extends Number & Comparable<?>> WhereBooleanBuilder notNullEq(T param, NumberPath<T> path) {
        if (param != null) {
            builder.and(path.eq(param));
        }
        return this;
    }
    
    public WhereBooleanBuilder notBlankEq(String param, StringPath path) {
        if (StringUtils.isNoneBlank(param)) {
            builder.and(path.eq(param));
        }
        return this;
    }
    
    public <T extends Enum<T>> WhereBooleanBuilder notNullEq(T param, EnumPath<T> path) {
        if (param != null) {
            builder.and(path.eq(param));
        }
        return this;
    }
    
    public WhereBooleanBuilder notBlankContains(String param, StringPath path) {
        if (StringUtils.isNoneBlank(param)) {
            builder.and(path.contains(param));
        }
        return this;
    }
}
