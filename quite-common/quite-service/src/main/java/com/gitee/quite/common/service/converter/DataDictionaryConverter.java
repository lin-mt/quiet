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

package com.gitee.quite.common.service.converter;

import com.gitee.quite.common.service.base.DataDictionary;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据库枚举转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DataDictionaryConverter<T extends DataDictionary<T>>
        implements AttributeConverter<DataDictionary<T>, String> {
    
    @Override
    public String convertToDatabaseColumn(DataDictionary<T> attribute) {
        return attribute == null ? null : attribute.getCode();
    }
    
    @Override
    public DataDictionary<T> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        DataDictionary<T> dataDictionary = new DataDictionary<>();
        dataDictionary.setCode(dbData);
        return dataDictionary;
    }
}
