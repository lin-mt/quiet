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

package com.gitee.quiet.common.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class QuietResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    private final ResourceServerProperties resourceServerProperties;
    
    private final AuthorizationServerProperties authorizationServerProperties;
    
    private final ObjectMapper objectMapper;
    
    public QuietResourceServerConfig(ResourceServerProperties resourceServerProperties,
            AuthorizationServerProperties authorizationServerProperties, ObjectMapper objectMapper) {
        this.resourceServerProperties = resourceServerProperties;
        this.authorizationServerProperties = authorizationServerProperties;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenService());
    }
    
    @Bean
    @Primary
    public RemoteTokenServices tokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new QuietUserAuthenticationConverter(objectMapper));
        tokenService.setAccessTokenConverter(accessTokenConverter);
        tokenService.setClientId(resourceServerProperties.getClientId());
        tokenService.setClientSecret(resourceServerProperties.getClientSecret());
        tokenService.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenAccess());
        return tokenService;
    }
    
}