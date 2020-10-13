package com.gitee.quite.common.service.base;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 数据字典枚举基类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Dictionary <T extends Dictionary<T>>{
    
    /**
     * code.
     *
     * @return 编码值
     */
    String getCode();
    
    default boolean equal(Dictionary<T> other){
        if (other == null) {
            return false;
        }
        return getCode().equals(other.getCode());
    }
    
    /**
     * 值.
     *
     * @return 前端显示的值
     */
    default String getValue() {
        return getCode();
    }
    
}
