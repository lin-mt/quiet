package com.gitee.quite.common.service.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface EnumDictionary<T extends Enum<?> & EnumDictionary<T>> extends Dictionary<T> {
    
    String PREFIX = "enum.";
    
    static String buildEnumMessageSourceKey(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("数据字典编码不能为空！");
        }
        return PREFIX + code;
    }
    
    @Override
    default String getCode() {
        return getClass().getSimpleName() + "." + ((Enum<?>) this).name();
    }
}
