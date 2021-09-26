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

package com.gitee.quiet.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gitee.quiet.common.core.json.BeforeObjectMapperInjection;
import com.gitee.quiet.web.json.JacksonConfigBasePackage;
import com.gitee.quiet.web.json.module.QuietSimpleModule;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

/**
 * Jackson配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@ComponentScan(basePackageClasses = JacksonConfigBasePackage.class)
public class JacksonConfig {
    
    public static final String QUIET_MODULE_NAME = "QuietSimpleModule";
    
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder,
            List<BeforeObjectMapperInjection> beforeObjectMapperInjections) {
        final ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        QuietSimpleModule module = new QuietSimpleModule(QUIET_MODULE_NAME);
        // 日期序列化与反序列化
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        // 时间序列化与反序列化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        objectMapper.registerModule(module);
        if (CollectionUtils.isNotEmpty(beforeObjectMapperInjections)) {
            Collections.sort(beforeObjectMapperInjections);
            for (BeforeObjectMapperInjection beforeObjectMapperInjection : beforeObjectMapperInjections) {
                if (beforeObjectMapperInjection != null) {
                    beforeObjectMapperInjection.config(objectMapper);
                }
            }
        }
        
        return objectMapper;
    }
    
}
