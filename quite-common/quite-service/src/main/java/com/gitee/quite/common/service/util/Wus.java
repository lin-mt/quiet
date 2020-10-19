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
