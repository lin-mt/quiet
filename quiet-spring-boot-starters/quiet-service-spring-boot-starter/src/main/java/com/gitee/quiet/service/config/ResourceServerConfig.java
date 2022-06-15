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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.service.security.converter.UserAuthenticationConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 各个资源端配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties(AuthorizationServerProperties.class)
@ConditionalOnMissingBean(value = AuthorizationServerConfigurer.class)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties resourceServerProperties;

    private final AuthorizationServerProperties authorizationServerProperties;

    private final ObjectMapper objectMapper;

    public ResourceServerConfig(ResourceServerProperties resourceServerProperties,
        AuthorizationServerProperties authorizationServerProperties, ObjectMapper objectMapper) {
        this.resourceServerProperties = resourceServerProperties;
        this.authorizationServerProperties = authorizationServerProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(remoteTokenService());
    }

    @Bean
    @Primary
    public RemoteTokenServices remoteTokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new UserAuthenticationConverter(objectMapper));
        tokenService.setAccessTokenConverter(accessTokenConverter);
        tokenService.setClientId(resourceServerProperties.getClientId());
        tokenService.setClientSecret(resourceServerProperties.getClientSecret());
        tokenService.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenAccess());
        return tokenService;
    }

}