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

package com.gitee.quiet.common.service.base;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface EnumDictionary<T extends Enum<?> & EnumDictionary<T>> extends Dictionary<T> {
    
    String PREFIX = "enum.";
    
    static String buildEnumMessageSourceKey(String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("数据字典编码不能为空！");
        }
        return PREFIX + code;
    }
    
    @Override
    default String getCode() {
        return getClass().getSimpleName() + "." + ((Enum<?>) this).name();
    }
}
