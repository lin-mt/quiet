package com.gitee.quite.common.service.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据字典枚举基类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Dictionary {
    
    String PREFIX = "database.";
    
    static String buildDatabaseMessageSourceKey(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("数据字典编码不能为空！");
        }
        return PREFIX + code;
    }
    
    /**
     * code.
     *
     * @return 编码值
     */
    default String getCode() {
        return null;
    }
    
    /**
     * 值.
     *
     * @return 前端显示的值
     */
    default String getValue() {
        return null;
    }
    
}
