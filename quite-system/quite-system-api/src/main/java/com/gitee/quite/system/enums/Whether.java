package com.gitee.quite.system.enums;

import com.gitee.quite.common.service.base.DictionaryEnum;

/**
 * 是否.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum Whether implements DictionaryEnum {
    
    /**
     * 是
     */
    YES("是"),
    
    /**
     * 否
     */
    NO("否");
    
    private final String value;
    
    Whether(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
}
