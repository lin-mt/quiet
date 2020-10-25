/*
 * Copyright 2020 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quite.common.service.result;

import com.gitee.quite.common.service.constant.CurdCode;

/**
 * Curd 结果类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum CurdType {
    
    /**
     * 新增成功.
     */
    CREATE_SUCCESS(CurdCode.CREATE_SUCCESS),
    /**
     * 新增失败.
     */
    CREATE_FAILURE(CurdCode.CREATE_FAILURE),
    /**
     * 更新成功.
     */
    UPDATE_SUCCESS(CurdCode.UPDATE_SUCCESS),
    /**
     * 更新失败.
     */
    UPDATE_FAILURE(CurdCode.UPDATE_FAILURE),
    /**
     * 查询成功.
     */
    RETRIEVE_SUCCESS(CurdCode.RETRIEVE_SUCCESS),
    /**
     * 查询失败.
     */
    RETRIEVE_FAILURE(CurdCode.RETRIEVE_FAILURE),
    /**
     * 删除成功.
     */
    DELETE_SUCCESS(CurdCode.DELETE_SUCCESS),
    /**
     * 删除失败.
     */
    DELETE_FAILURE(CurdCode.DELETE_FAILURE),
    ;
    
    
    private final String code;
    
    CurdType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
