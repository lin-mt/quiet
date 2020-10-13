package com.gitee.quite.common.service.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库的数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DatabaseDictionary<T extends DatabaseDictionary<T>> implements Dictionary<T> {
    
    public static final String PREFIX = "database.";
    
    private String code;
    
    private String value;
    
    public static String buildDatabaseMessageSourceKey(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("数据字典编码不能为空！");
        }
        return PREFIX + code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String getValue() {
        return value;
    }
}
