/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.common.service.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;

/**
 * 查询条件构造工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class Wus {
    
    private Wus() {
    }
    
    public static <T extends Number & Comparable<?>> void NotNullEq(T param, NumberPath<T> path,
            BooleanBuilder builder) {
        if (param != null) {
            builder.and(path.eq(param));
        }
    }
    
    public static void NotBlankEq(String param, StringPath path, BooleanBuilder builder) {
        if (StringUtils.isNoneBlank(param)) {
            builder.and(path.eq(param));
        }
    }
    
    public static <T extends Enum<T>> void NotNullEq(T param, EnumPath<T> path, BooleanBuilder builder) {
        if (param != null) {
            builder.and(path.eq(param));
        }
    }
    
    public static void NotBlankContains(String param, StringPath path, BooleanBuilder builder) {
        if (StringUtils.isNoneBlank(param)) {
            builder.and(path.contains(param));
        }
    }
    
}
