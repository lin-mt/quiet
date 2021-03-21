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

package com.gitee.quiet.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.service.config.UserAuthenticationConverter;
import com.gitee.quiet.system.service.QuietClientService;
import com.gitee.quiet.system.service.QuietUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    private final QuietUserService userService;
    
    private final QuietClientService clientService;
    
    private final AuthenticationManager authenticationManager;
    
    private final TokenStore redisTokenStore;
    
    private final ObjectMapper objectMapper;
    
    public AuthorizationServerConfig(QuietUserService userService, QuietClientService clientService,
            AuthenticationManager authenticationManager, TokenStore redisTokenStore, ObjectMapper objectMapper) {
        this.userService = userService;
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.redisTokenStore = redisTokenStore;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        UserAuthenticationConverter authenticationConverter = new UserAuthenticationConverter(objectMapper);
        authenticationConverter.setUserDetailsService(userService);
        accessTokenConverter.setUserTokenConverter(authenticationConverter);
        // @formatter:off
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .accessTokenConverter(accessTokenConverter)
                .tokenStore(redisTokenStore);
        // @formatter:on
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }
    
}
