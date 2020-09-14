package com.gitee.quite.enums;

import com.gitee.quite.base.DictionaryEnum;

/**
 * 性别.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum Gender implements DictionaryEnum {
    
    
    /**
     * 男
     */
    MALE("男"),
    
    /**
     * 女
     */
    FEMALE("女"),
    ;
    
    private final String value;
    
    Gender(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
}
