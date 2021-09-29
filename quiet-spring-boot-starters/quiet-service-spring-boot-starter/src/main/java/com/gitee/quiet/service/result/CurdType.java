/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.service.result;

import com.gitee.quiet.common.constant.service.MessageSourceCode;

/**
 * Curd 结果类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum CurdType {
    
    /**
     * 新增成功.
     */
    CREATE_SUCCESS(MessageSourceCode.Curd.CREATE_SUCCESS),
    /**
     * 新增失败.
     */
    CREATE_FAILURE(MessageSourceCode.Curd.CREATE_FAILURE),
    /**
     * 更新成功.
     */
    UPDATE_SUCCESS(MessageSourceCode.Curd.UPDATE_SUCCESS),
    /**
     * 更新失败.
     */
    UPDATE_FAILURE(MessageSourceCode.Curd.UPDATE_FAILURE),
    /**
     * 查询成功.
     */
    READ_SUCCESS(MessageSourceCode.Curd.READ_SUCCESS),
    /**
     * 查询失败.
     */
    READ_FAILURE(MessageSourceCode.Curd.READ_FAILURE),
    /**
     * 删除成功.
     */
    DELETE_SUCCESS(MessageSourceCode.Curd.DELETE_SUCCESS),
    /**
     * 删除失败.
     */
    DELETE_FAILURE(MessageSourceCode.Curd.DELETE_FAILURE),
    ;
    
    private final String code;
    
    CurdType(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}