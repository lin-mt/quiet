package com.gitee.quite.common.service.base;

/**
 * 数据库的数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DatabaseDictionary implements Dictionary{
    
    private String code;
    private String value;
    
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
