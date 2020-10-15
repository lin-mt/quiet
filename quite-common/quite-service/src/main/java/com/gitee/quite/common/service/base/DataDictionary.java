package com.gitee.quite.common.service.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库的数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DataDictionary<T extends DataDictionary<T>> implements Dictionary<T> {
    
    public static final String PREFIX = "database.";
    
    private String code;
    
    private String value;
    
    public static String buildDatabaseMessageSourceKey(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("数据字典编码不能为空！");
        }
        return PREFIX + code;
    }
    
    protected T of(String code) {
        throw new RuntimeException("子类需要实现该方法");
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
