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

package com.gitee.quiet.common.service.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gitee.quiet.common.service.json.JacksonConfigBasePackage;
import com.gitee.quiet.common.service.json.filter.HasRoleAnnotationFilter;
import com.gitee.quiet.common.service.json.filter.JsonFilterName;
import com.gitee.quiet.common.service.json.module.QuietSimpleModule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Jackson配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
@ComponentScan(basePackageClasses = JacksonConfigBasePackage.class)
public class JacksonConfig {
    
    private final ObjectMapper objectMapper;
    
    public JacksonConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Bean
    public Module module() {
        return new QuietSimpleModule();
    }
    
    @PostConstruct
    public void postConstruct() {
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter(JsonFilterName.HAS_ROLE, new HasRoleAnnotationFilter());
        objectMapper.setFilterProvider(filterProvider);
    }
}
