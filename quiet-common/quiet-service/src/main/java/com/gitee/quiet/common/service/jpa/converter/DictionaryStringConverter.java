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

package com.gitee.quiet.common.service.jpa.converter;

import com.gitee.quiet.common.service.constant.ServiceConstant;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据字典与数据库的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DictionaryStringConverter implements AttributeConverter<Dictionary, String> {
    
    @Override
    public String convertToDatabaseColumn(Dictionary attribute) {
        if (attribute != null) {
            return attribute.getType() + ServiceConstant.Dictionary.SPLIT + attribute.getKey();
        }
        return null;
    }
    
    @Override
    public Dictionary convertToEntityAttribute(String dbData) {
        if (StringUtils.isNoneBlank(dbData)) {
            String[] split = dbData.split(ServiceConstant.Dictionary.SPLIT_REGEX);
            if (split.length < ServiceConstant.Dictionary.ARRAY_MIN_LENGTH) {
                throw new IllegalArgumentException("数据库数据字典有误，数据字典必须包含type和key");
            }
            Dictionary dictionary = new Dictionary();
            dictionary.setType(split[0]);
            dictionary.setKey(split[1]);
            return dictionary;
        }
        return null;
    }
}
