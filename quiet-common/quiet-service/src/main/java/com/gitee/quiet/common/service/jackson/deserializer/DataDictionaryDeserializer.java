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

package com.gitee.quiet.common.service.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gitee.quiet.common.service.base.DataDictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 数据字典反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class DataDictionaryDeserializer<T extends DataDictionary<T>> extends JsonDeserializer<DataDictionary<T>> {
    
    @Override
    public DataDictionary<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        if (!StringUtils.isBlank(jsonParser.getText())) {
            DataDictionary<T> dictionary = new DataDictionary<>();
            dictionary.setCode(jsonParser.getText());
            return dictionary;
        }
        return null;
    }
}
