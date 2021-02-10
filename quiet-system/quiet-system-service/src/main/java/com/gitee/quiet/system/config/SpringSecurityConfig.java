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

package com.gitee.quiet.system.config;

import com.gitee.quiet.system.constant.Url;
import com.gitee.quiet.system.filter.LoginByAccountFilter;
import com.gitee.quiet.system.handler.AuthenticationJsonEntryPointHandler;
import com.gitee.quiet.system.handler.ResultAccessDeniedHandler;
import com.gitee.quiet.system.service.QuietUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Spring Security 配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final QuietUserService userService;
    
    private final LoginByAccountFilter loginByAccountFilter;
    
    private final ResultAccessDeniedHandler accessDeniedHandler;
    
    private final AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler;
    
    private final LogoutHandler logoutHandler;
    
    private final LogoutSuccessHandler logoutSuccessHandler;
    
    public SpringSecurityConfig(QuietUserService userService, LoginByAccountFilter loginByAccountFilter,
            ResultAccessDeniedHandler accessDeniedHandler,
            AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler, LogoutHandler logoutHandler,
            LogoutSuccessHandler logoutSuccessHandler) {
        this.userService = userService;
        this.loginByAccountFilter = loginByAccountFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationJsonEntryPointHandler = authenticationJsonEntryPointHandler;
        this.logoutHandler = logoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }
    
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(Url.REGISTER);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationJsonEntryPointHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .logout()
                .logoutUrl(Url.LOGOUT)
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler);
//        // @formatter:on
        http.addFilterAt(loginByAccountFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
}
