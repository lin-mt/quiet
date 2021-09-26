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

import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.io.Serializable;

public final class AttributeConverterInterceptor {
    
    private AttributeConverterInterceptor() {
    }
    
    @RuntimeType
    public static <T extends Enum<T> & JpaCustomEnum<V>, V extends Serializable> V convertToDatabaseColumn(
            T attribute) {
        return attribute == null ? null : attribute.getValue();
    }
    
    @RuntimeType
    public static <T extends Enum<T> & JpaCustomEnum<V>, V extends Serializable> T convertToEntityAttribute(V dbData,
            @FieldValue("enumType") Class<T> enumType) {
        return dbData == null ? null : JpaCustomEnum.valueToEnum(enumType, dbData);
    }
}
