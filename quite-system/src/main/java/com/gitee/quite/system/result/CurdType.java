package com.gitee.quite.system.result;

import com.gitee.quite.system.constant.CommonCode;

/**
 * Curd 结果类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum CurdType {
    
    /**
     * 新增成功.
     */
    CREATE_SUCCESS(CommonCode.CREATE_SUCCESS),
    /**
     * 新增失败.
     */
    CREATE_FAILURE(CommonCode.CREATE_FAILURE),
    /**
     * 更新成功.
     */
    UPDATE_SUCCESS(CommonCode.UPDATE_SUCCESS),
    /**
     * 更新失败.
     */
    UPDATE_FAILURE(CommonCode.UPDATE_FAILURE),
    /**
     * 查询成功.
     */
    RETRIEVE_SUCCESS(CommonCode.RETRIEVE_SUCCESS),
    /**
     * 查询失败.
     */
    RETRIEVE_FAILURE(CommonCode.RETRIEVE_FAILURE),
    /**
     * 删除成功.
     */
    DELETE_SUCCESS(CommonCode.DELETE_SUCCESS),
    /**
     * 删除失败.
     */
    DELETE_FAILURE(CommonCode.DELETE_FAILURE),
    ;
    
    
    private final String code;
    
    CurdType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
