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

package com.gitee.quite.system.constant;

/**
 * 跟账号相关的通用 Code.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class AccountCode {
    
    public static final String PREFIX = "account.";
    
    public static final String NO_LOGIN = buildCode("no.login");
    
    public static final String LOGIN_SUCCESS = buildCode("login.success");
    
    public static final String LOGIN_FAILURE = buildCode("login.failure");
    
    public static final String LOGOUT_SUCCESS = buildCode("logout.success");
    
    public static final String NO_PERMISSION = buildCode("no.permission");
    
    private AccountCode() {
    }
    
    private static String buildCode(String code) {
        return PREFIX + code;
    }
}
