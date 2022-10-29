/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.service.config;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.common.core.json.BeforeObjectMapperInjection;
import com.gitee.quiet.jpa.properties.EnumScanPath;
import com.gitee.quiet.service.advice.AdvicePackage;
import com.gitee.quiet.service.aware.QuietAuditorAware;
import com.gitee.quiet.service.converter.StringToDictConverter;
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
  public StringToDictConverter stringToDictConverter() {
    return new StringToDictConverter();
  }

  @Bean
  public EnumScanPath serviceEnumPath() {
    return ServiceEnumsPackage.class::getPackageName;
  }

  @Bean
  @ConditionalOnProperty(
      prefix = "spring.jackson",
      name = "property-naming-strategy",
      havingValue = "SNAKE_CASE")
  public FilterRegistrationBean<GetMethodQueryParamSnakeCaseFilter>
      getMethodQueryParamSnakeCaseFilter() {
    FilterRegistrationBean<GetMethodQueryParamSnakeCaseFilter> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    registrationBean.setFilter(new GetMethodQueryParamSnakeCaseFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
