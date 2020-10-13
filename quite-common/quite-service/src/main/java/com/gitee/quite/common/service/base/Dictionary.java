package com.gitee.quite.common.service.base;

/**
 * 数据字典枚举基类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Dictionary<T extends Dictionary<T>> {
    
    /**
     * code.
     *
     * @return 编码值
     */
    String getCode();
    
    /**
     * 值.
     *
     * @return 前端显示的值
     */
    default String getValue() {
        return getCode();
    }
    
    default boolean equal(Dictionary<T> other) {
        if (other == null) {
            return false;
        }
        return getCode().equals(other.getCode());
    }
    
    /**
     * 数据字典包含的字段枚举
     */
    enum Field {
        /**
         * 编码
         */
        CODE("code"),
        /**
         * 值
         */
        VALUE("value"),
        ;
        
        private final String fieldName;
        
        Field(String fieldName) {
            this.fieldName = fieldName;
        }
        
        public String getFieldName() {
            return fieldName;
        }
    }
    
}
