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

import com.gitee.quiet.service.security.QuietAccessDecisionManager;
import com.gitee.quiet.service.security.QuietSecurityMetadataSource;
import com.gitee.quiet.service.security.UrlPermissionService;
import com.gitee.quiet.service.security.filter.QuietUrlSecurityFilter;
import com.gitee.quiet.service.security.properties.QuietSecurityProperties;
import java.util.Optional;
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

/**
 * Web 安全配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Order(101)
@Configuration
@ConditionalOnBean(UrlPermissionService.class)
@EnableConfigurationProperties(QuietSecurityProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UrlPermissionService urlPermissionService;

    private final GrantedAuthorityDefaults grantedAuthorityDefaults;

    public WebSecurityConfig(Optional<UrlPermissionService> urlPermissionService,
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
    @ConditionalOnBean(RoleHierarchy.class)
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
