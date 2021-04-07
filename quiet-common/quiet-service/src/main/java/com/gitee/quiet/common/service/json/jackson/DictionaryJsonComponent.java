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

package com.gitee.quiet.common.service.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.util.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Map;

/**
 * 数据字典序列化与反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class DictionaryJsonComponent {
    
    /**
     * com.gitee.quiet.common.service.jpa.entity.Dictionary 反序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class DictionaryDeserializer extends JsonDeserializer<Dictionary> {
        
        @Override
        public Dictionary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (StringUtils.isNoneBlank(jsonParser.getText())) {
                return Dictionary.convertFromString(jsonParser.getText());
            }
            return null;
        }
    }
    
    /**
     * com.gitee.quiet.common.service.jpa.entity.Dictionary 序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class DictionarySerializer extends JsonSerializer<Dictionary> {
        
        @Override
        public void serialize(Dictionary dictionaryValue, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
            if (dictionaryValue != null) {
                if (dictionaryValue.getId() != null) {
                    jsonGenerator.writeObject(GsonUtil.fromJson(GsonUtil.toJson(dictionaryValue), Map.class));
                } else {
                    jsonGenerator.writeString(Dictionary.convertToString(dictionaryValue));
                }
            }
        }
    }
    
}
