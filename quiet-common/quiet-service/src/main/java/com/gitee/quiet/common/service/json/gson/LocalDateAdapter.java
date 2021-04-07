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

package com.gitee.quiet.common.service.json.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate 序列化与反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {
    
    @Override
    public LocalDate deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        String timestamp = element.getAsString();
        if (StringUtils.isBlank(timestamp)) {
            return null;
        }
        return LocalDate.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    @Override
    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        if (date == null) {
            return JsonNull.INSTANCE;
        }
        return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
    
}