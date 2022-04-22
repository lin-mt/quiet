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

package com.gitee.quiet.jpa.converter;

import com.gitee.quiet.jpa.entity.Dictionary;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据字典与数据库的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DictionaryStringConverter implements AttributeConverter<Dictionary<?>, String> {

    @Override
    public String convertToDatabaseColumn(Dictionary<?> attribute) {
        return Dictionary.convertToString(attribute);
    }

    @Override
    public Dictionary<?> convertToEntityAttribute(String dbData) {
        return Dictionary.convertFromString(dbData);
    }
}
