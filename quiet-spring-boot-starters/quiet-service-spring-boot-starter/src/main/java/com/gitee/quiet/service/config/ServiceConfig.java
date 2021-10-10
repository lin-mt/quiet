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

package com.gitee.quiet.service.config;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.common.core.json.BeforeObjectMapperInjection;
import com.gitee.quiet.jpa.properties.EnumScanPath;
import com.gitee.quiet.service.advice.AdvicePackage;
import com.gitee.quiet.service.aware.QuietAuditorAware;
import com.gitee.quiet.service.converter.StringToDictionaryConverter;
import com.gitee.quiet.service.enums.ServiceEnumsPackage;
import com.gitee.quiet.service.filter.GetMethodQueryParamSnakeCaseFilter;
import com.gitee.quiet.service.json.filter.HasRoleAnnotationFilter;
import com.gitee.quiet.service.json.filter.JsonFilterName;
import com.gitee.quiet.service.json.module.ServiceSimpleModule;
import com.gitee.quiet.service.utils.SpringUtil;
import com.gitee.quiet.web.config.JacksonConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

/**
 * 所有服务的共同配置信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@EnableDiscoveryClient
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackageClasses = AdvicePackage.class)
@AutoConfigureBefore(JacksonConfig.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class ServiceConfig {
    
    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }
    
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(RoleNames.ROLE_PREFIX);
    }
    
    @Bean
    public BeforeObjectMapperInjection serviceSimpleModule() {
        return objectMapper -> {
            ServiceSimpleModule module = new ServiceSimpleModule("ServiceSimpleModule");
            objectMapper.registerModule(module);
            SimpleFilterProvider filterProvider = new SimpleFilterProvider();
            filterProvider.addFilter(JsonFilterName.HAS_ROLE, new HasRoleAnnotationFilter());
            objectMapper.setFilterProvider(filterProvider);
        };
    }
    
    @Bean
    @ConditionalOnMissingBean(AuditorAware.class)
    public QuietAuditorAware auditorAware() {
        return new QuietAuditorAware();
    }
    
    @Bean
    public StringToDictionaryConverter stringToDictionaryConverter() {
        return new StringToDictionaryConverter();
    }
    
    @Bean
    public EnumScanPath serviceEnumPath() {
        return ServiceEnumsPackage.class::getPackageName;
    }
    
    @Bean
    @ConditionalOnProperty(prefix = "spring.jackson", name = "property-naming-strategy", havingValue = "SNAKE_CASE")
    public FilterRegistrationBean<GetMethodQueryParamSnakeCaseFilter> getMethodQueryParamSnakeCaseFilter() {
        FilterRegistrationBean<GetMethodQueryParamSnakeCaseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.setFilter(new GetMethodQueryParamSnakeCaseFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

