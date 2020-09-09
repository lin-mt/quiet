package com.gitee.quite.enums;

import com.gitee.quite.base.DatabaseDictionaryEnum;

/**
 * 性别.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum Gender implements DatabaseDictionaryEnum {
    /**
     * 男
     */
    MALE("010201", "男", "男性"),
    
    /**
     * 女
     */
    FEMALE("010202", "女", "女性"),
    ;
    
    private final String code;
    
    private final String value;
    
    private final String description;
    
    Gender(String code, String value, String description) {
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
