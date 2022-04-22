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

package com.gitee.quiet.common.constant.service;

/**
 * 国际化 MessageSource Code 常量
 */
public class MessageSourceCode {

    /**
     * 常用的编码前缀
     */
    public static final String COMMON_CODE_PREFIX = "common";

    /**
     * 未知编码前缀
     */
    public static final String UNKNOWN_CODE = "common.unknown.code";

    private MessageSourceCode() {
    }

    public static final class Curd {

        public static final String CREATE_SUCCESS = "common.curd.create.success";

        public static final String CREATE_FAILURE = "common.curd.create.failure";

        public static final String UPDATE_SUCCESS = "common.curd.update.success";

        public static final String UPDATE_FAILURE = "common.curd.update.failure";

        public static final String READ_SUCCESS = "common.curd.read.success";

        public static final String READ_FAILURE = "common.curd.read.failure";

        public static final String DELETE_SUCCESS = "common.curd.delete.success";

        public static final String DELETE_FAILURE = "common.curd.delete.failure";

        private Curd() {
        }
    }

    public static final class Account {

        public static final String NO_LOGIN = "common.account.no.login";

        public static final String LOGIN_SUCCESS = "common.account.login.success";

        public static final String LOGIN_FAILURE = "common.account.login.failure";

        public static final String LOGOUT_SUCCESS = "common.account.logout.success";

        public static final String NO_PERMISSION = "common.account.no.permission";

        private Account() {
        }
    }
}
