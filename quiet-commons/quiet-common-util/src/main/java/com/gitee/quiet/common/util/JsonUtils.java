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

package com.gitee.quiet.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Json 工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class JsonUtils {
    
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    static {
        SimpleModule module = new SimpleModule("JsonUtils");
        // 日期序列化与反序列化
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        // 时间序列化与反序列化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        OBJECT_MAPPER.registerModule(module);
    }
    
    private JsonUtils() {
    }
    
    @SneakyThrows
    public static String toJsonString(Object value) {
        return OBJECT_MAPPER.writeValueAsString(value);
    }
    
    @SneakyThrows
    public static <T> T readValue(String value, TypeReference<T> reference) {
        return OBJECT_MAPPER.readValue(value, reference);
    }
}
