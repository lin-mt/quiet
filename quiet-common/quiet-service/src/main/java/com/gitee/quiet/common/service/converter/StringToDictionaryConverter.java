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

package com.gitee.quiet.common.service.converter;

import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Nullable;

/**
 * String 转数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class StringToDictionaryConverter implements Converter<String, Dictionary<?>> {
    
    @Override
    public Dictionary<?> convert(@Nullable String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        return Dictionary.convertFromString(source);
    }
}
