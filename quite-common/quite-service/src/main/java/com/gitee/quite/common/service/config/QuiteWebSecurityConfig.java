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

package com.gitee.quite.common.service.config;

import com.gitee.quite.common.service.security.QuiteAccessDecisionManager;
import com.gitee.quite.common.service.security.QuiteSecurityMetadataSource;
import com.gitee.quite.common.service.security.QuiteSecurityProperties;
import com.gitee.quite.common.service.security.QuiteUrlSecurityFilter;
import com.gitee.quite.common.service.security.UrlPermissionService;
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
@EnableConfigurationProperties(QuiteSecurityProperties.class)
public class QuiteWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UrlPermissionService urlPermissionService;
    
    private final GrantedAuthorityDefaults grantedAuthorityDefaults;
    
    public QuiteWebSecurityConfig(Optional<UrlPermissionService> urlPermissionService,
            Optional<GrantedAuthorityDefaults> grantedAuthorityDefaults) {
        this.urlPermissionService = urlPermissionService.orElseThrow();
        this.grantedAuthorityDefaults = grantedAuthorityDefaults.orElse(null);
    }
    
    @Override
    protected void configure(HttpSecurity http) {
        http.addFilterBefore(quiteUrlSecurityFilter(), FilterSecurityInterceptor.class);
    }
    
    @Bean
    public QuiteSecurityProperties quiteSecurityProperties() {
        return new QuiteSecurityProperties();
    }
    
    @Bean
    public QuiteAccessDecisionManager quiteAccessDecisionManager(RoleHierarchy roleHierarchy) {
        return new QuiteAccessDecisionManager(roleHierarchy);
    }
    
    @Bean
    public QuiteUrlSecurityFilter quiteUrlSecurityFilter() {
        return new QuiteUrlSecurityFilter(quiteSecurityProperties(), quiteSecurityMetadataSource());
    }
    
    @Bean
    public QuiteSecurityMetadataSource quiteSecurityMetadataSource() {
        return new QuiteSecurityMetadataSource(urlPermissionService, grantedAuthorityDefaults);
    }
}
