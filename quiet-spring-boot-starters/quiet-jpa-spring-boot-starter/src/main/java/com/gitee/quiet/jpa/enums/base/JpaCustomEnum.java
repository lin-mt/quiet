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

package com.gitee.quiet.jpa.enums.base;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

interface JpaCustomEnum<T extends Serializable> {
    
    /**
     * 数据库字段至枚举的转换工具。
     */
    static <V extends Serializable, E extends Enum<E> & JpaCustomEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        return Stream.of(enumType.getEnumConstants()).filter(item -> Objects.equals(item.getValue(), value)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown enum value: " + value + " for type: " + enumType.getSimpleName()));
    }
    
    /**
     * 存储数据库的值
     *
     * @return 数据库值
     */
    T getValue();
    
}
