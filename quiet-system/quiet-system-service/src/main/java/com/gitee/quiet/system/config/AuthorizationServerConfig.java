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

package com.gitee.quiet.system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.service.security.converter.UserAuthenticationConverter;
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

  public AuthorizationServerConfig(
      QuietUserService userService,
      QuietClientService clientService,
      AuthenticationManager authenticationManager,
      TokenStore redisTokenStore,
      ObjectMapper objectMapper) {
    this.userService = userService;
    this.clientService = clientService;
    this.authenticationManager = authenticationManager;
    this.redisTokenStore = redisTokenStore;
    this.objectMapper = objectMapper;
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
    UserAuthenticationConverter authenticationConverter =
        new UserAuthenticationConverter(objectMapper);
    authenticationConverter.setUserDetailsService(userService);
    accessTokenConverter.setUserTokenConverter(authenticationConverter);
    // @formatter:off
    endpoints
        .authenticationManager(authenticationManager)
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
