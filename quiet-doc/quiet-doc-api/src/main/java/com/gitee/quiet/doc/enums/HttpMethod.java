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

package com.gitee.quiet.doc.enums;

import com.gitee.quiet.jpa.enums.base.StringEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http 请求方法类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum HttpMethod implements StringEnum {

    GET("get"),

    HEAD("head"),

    POST("post"),

    PUT("put"),

    DELETE("delete"),

    OPTIONS("options"),

    PATCH("patch");

    public final String value;
}
