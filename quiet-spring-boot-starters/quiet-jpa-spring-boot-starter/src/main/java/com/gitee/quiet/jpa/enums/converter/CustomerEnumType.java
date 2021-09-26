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

package com.gitee.quiet.jpa.enums.converter;

import com.gitee.quiet.jpa.enums.base.ByteEnum;
import com.gitee.quiet.jpa.enums.base.DoubleEnum;
import com.gitee.quiet.jpa.enums.base.FloatEnum;
import com.gitee.quiet.jpa.enums.base.IntegerEnum;
import com.gitee.quiet.jpa.enums.base.LongEnum;
import com.gitee.quiet.jpa.enums.base.ShortEnum;
import com.gitee.quiet.jpa.enums.base.StringEnum;
import lombok.Getter;

@Getter
public enum CustomerEnumType {
    
    BYTE(ByteEnum.class, Byte.class),
    DOUBLE(DoubleEnum.class, Double.class),
    FLOAT(FloatEnum.class, Float.class),
    INTEGER(IntegerEnum.class, Integer.class),
    LONG(LongEnum.class, Long.class),
    SHORT(ShortEnum.class, Short.class),
    STRING(StringEnum.class, String.class),
    ;
    
    private final Class<?> superClass;
    
    private final Class<?> valueClass;
    
    CustomerEnumType(Class<?> superClass, Class<?> valueClass) {
        this.superClass = superClass;
        this.valueClass = valueClass;
    }
}
