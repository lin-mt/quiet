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

package com.gitee.quiet.common.service.config;

import com.gitee.quiet.common.service.security.QuietAccessDecisionManager;
import com.gitee.quiet.common.service.security.QuietSecurityMetadataSource;
import com.gitee.quiet.common.service.security.QuietSecurityProperties;
import com.gitee.quiet.common.service.security.QuietUrlSecurityFilter;
import com.gitee.quiet.common.service.security.UrlPermissionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Optional;

/**
 * Web 安全配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Order(101)
@Configuration
@ConditionalOnBean(UrlPermissionService.class)
@EnableConfigurationProperties(QuietSecurityProperties.class)
public class QuietWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UrlPermissionService urlPermissionService;
    
    private final GrantedAuthorityDefaults grantedAuthorityDefaults;
    
    public QuietWebSecurityConfig(Optional<UrlPermissionService> urlPermissionService,
            Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults) {
        this.urlPermissionService = urlPermissionService.orElseThrow();
        this.grantedAuthorityDefaults = grantedAuthorityDefaults.orElse(null);
    }
    
    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterBefore(quietUrlSecurityFilter(), FilterSecurityInterceptor.class);
    }
    
    @Bean
    public QuietSecurityProperties quietSecurityProperties() {
        return new QuietSecurityProperties();
    }
    
    @Bean
    public QuietAccessDecisionManager quietAccessDecisionManager(RoleHierarchy roleHierarchy) {
        return new QuietAccessDecisionManager(roleHierarchy);
    }
    
    @Bean
    public QuietUrlSecurityFilter quietUrlSecurityFilter() {
        return new QuietUrlSecurityFilter(quietSecurityProperties(), quietSecurityMetadataSource());
    }
    
    @Bean
    public QuietSecurityMetadataSource quietSecurityMetadataSource() {
        return new QuietSecurityMetadataSource(urlPermissionService, grantedAuthorityDefaults);
    }
}
