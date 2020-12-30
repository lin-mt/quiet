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

package com.gitee.quiet.common.base.constant;

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
