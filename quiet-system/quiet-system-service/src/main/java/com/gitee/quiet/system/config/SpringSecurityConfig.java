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

import com.gitee.quiet.common.constant.service.Url;
import com.gitee.quiet.system.filter.LoginByAccountFilter;
import com.gitee.quiet.system.handler.AuthenticationJsonEntryPointHandler;
import com.gitee.quiet.system.handler.ResultAccessDeniedHandler;
import com.gitee.quiet.system.service.QuietUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

    private final LogoutHandler logoutHandler;

    private final AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    public SpringSecurityConfig(QuietUserService userService, LoginByAccountFilter loginByAccountFilter,
        ResultAccessDeniedHandler accessDeniedHandler, LogoutHandler logoutHandler,
        LogoutSuccessHandler logoutSuccessHandler,
        AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler) {
        this.userService = userService;
        this.loginByAccountFilter = loginByAccountFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutHandler = logoutHandler;
        this.authenticationJsonEntryPointHandler = authenticationJsonEntryPointHandler;
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
            .antMatchers(HttpMethod.OPTIONS)
            .permitAll()
            .antMatchers("/oauth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationJsonEntryPointHandler)
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout()
            .logoutUrl(Url.LOGOUT)
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(logoutSuccessHandler);
        // @formatter:on
        http.addFilterAt(loginByAccountFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
