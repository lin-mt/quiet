package com.gitee.quite.common.service.constant;

/**
 * 增删改查通用 Code.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class CurdCode {
    
    public static final String PREFIX = "curd.";
    
    public static final String CREATE_SUCCESS = buildCode("create.success");
    
    public static final String CREATE_FAILURE = buildCode("create.failure");
    
    public static final String UPDATE_SUCCESS = buildCode("update.success");
    
    public static final String UPDATE_FAILURE = buildCode("update.failure");
    
    public static final String RETRIEVE_SUCCESS = buildCode("retrieve.success");
    
    public static final String RETRIEVE_FAILURE = buildCode("retrieve.failure");
    
    public static final String DELETE_SUCCESS = buildCode("delete.success");
    
    public static final String DELETE_FAILURE = buildCode("delete.failure");
    
    private CurdCode() {
    }
    
    private static String buildCode(String code) {
        return CommonCode.PREFIX + PREFIX + code;
    }
}
