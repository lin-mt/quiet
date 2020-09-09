package com.gitee.quite.enums;

import com.gitee.quite.base.DatabaseDictionaryEnum;

/**
 * 是否.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum Whether implements DatabaseDictionaryEnum {
    /**
     * 是
     */
    YES("010101", "是", "是"),
    
    /**
     * 否
     */
    NO("010102", "否", "否");
    
    private final String code;
    
    private final String value;
    
    private final String description;
    
    Whether(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
}
