package com.gitee.quiet.common.service.constant;

/**
 * Service常量.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class ServiceConstant {
    
    private ServiceConstant() {
    }
    
    public static final class DataDictionary {
        
        private DataDictionary() {
        }
        
        // 分隔符
        public static final String SPLIT = ".";
        
        // 分割的格式为：${type}.${key}.${value}
        public static final String SPLIT_REGEX = "\\.";
        
        // 至少需要包含 type 和 key
        public static final Integer ARRAY_MIN_LENGTH = 2;
        
    }
}
