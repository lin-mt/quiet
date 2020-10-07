package com.gitee.quite.system.constant;

/**
 * 通用 Code.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class CommonCode {
    
    public static final String PREFIX = "common.";
    
    public static final String UNKNOWN_CODE = "unknown.code";
    
    public static final String CREATE_SUCCESS = buildCode("create.success");
    
    public static final String CREATE_FAILURE = buildCode("create.failure");
    
    public static final String UPDATE_SUCCESS = buildCode("update.success");
    
    public static final String UPDATE_FAILURE = buildCode("update.failure");
    
    public static final String RETRIEVE_SUCCESS = buildCode("retrieve.success");
    
    public static final String RETRIEVE_FAILURE = buildCode("retrieve.failure");
    
    public static final String DELETE_SUCCESS = buildCode("delete.success");
    
    public static final String DELETE_FAILURE = buildCode("delete.failure");
    
    private CommonCode() {
    }
    
    private static String buildCode(String code) {
        return PREFIX + code;
    }
    
    public static String removePrefix(String code) {
        return code.substring(PREFIX.length());
    }
}
