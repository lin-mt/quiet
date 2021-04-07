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

package com.gitee.quiet.common.service.util;

import com.gitee.quiet.common.service.json.gson.LocalDateAdapter;
import com.gitee.quiet.common.service.json.gson.LocalDateTimeAdapter;
import com.gitee.quiet.common.service.json.gson.LongAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Gson 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class GsonUtil {
    
    /**
     * Gson 实例
     */
    private static final Gson GSON = new GsonBuilder()
            // @formatter:off
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Long.class, new LongAdapter()).create();
            // @formatter:on
    
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return GSON.fromJson(json, classOfT);
    }
    
    public static String toJson(Object src) {
        return GSON.toJson(src);
    }
}
