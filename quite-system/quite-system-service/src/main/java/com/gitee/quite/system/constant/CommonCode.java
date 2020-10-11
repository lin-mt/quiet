package com.gitee.quite.system.constant;

/**
 * 通用 Code.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class CommonCode {
    
    public static final String PREFIX = "common.";
    
    public static final String UNKNOWN_CODE = "unknown.code";
    
    private CommonCode() {
    }
    
    public static String removePrefix(String code) {
        return code.substring(PREFIX.length());
    }
}
