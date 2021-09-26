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
 * 字段类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum FieldType implements StringEnum {
    
    /**
     * 字符串
     */
    STRING("string"),
    
    /**
     * 对象
     */
    OBJECT("object"),
    
    /**
     * 数组
     */
    ARRAY("array"),
    
    /**
     * 数字
     */
    NUMBER("number"),
    
    /**
     * 整型
     */
    INTEGER("integer"),
    
    /**
     * 布尔类型
     */
    BOOLEAN("boolean"),
    
    /**
     * 枚举
     */
    ENUM("enum");
    
    private final String value;
}
