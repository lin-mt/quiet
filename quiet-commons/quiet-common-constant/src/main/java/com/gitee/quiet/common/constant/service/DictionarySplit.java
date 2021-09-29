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

public final class DictionarySplit {
    
    /**
     * 分隔符
     */
    public static final String SEPARATOR = ".";
    
    /**
     * 拆分字符串的正则表达式，字符串的格式为：${type}.${key}.${value}
     */
    public static final String REGEX = "\\.";
    
    /**
     * 分割后至少包含 type 和 key
     */
    public static final Integer ARRAY_MIN_LENGTH = 2;
    
    private DictionarySplit() {
    }
    
}