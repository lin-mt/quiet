/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.common.service.json.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Boolean 序列化与反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class BooleanJsonComponent {
    
    /**
     * java.lang.Boolean 反序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class BooleanDeserializer extends JsonDeserializer<Boolean> {
        
        @Override
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (StringUtils.isNoneBlank(jsonParser.getText())) {
                return Boolean.parseBoolean(jsonParser.getText());
            }
            return null;
        }
    }
    
    /**
     * java.lang.Boolean 序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class BooleanSerializer extends JsonSerializer<Boolean> {
        
        @Override
        public void serialize(Boolean booleanValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (booleanValue != null) {
                jsonGenerator.writeString(booleanValue.toString());
            }
        }
    }
}
