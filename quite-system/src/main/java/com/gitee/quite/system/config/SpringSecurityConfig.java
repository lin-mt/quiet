package com.gitee.quite.system.config;

import com.gitee.quite.system.constant.Url;
import com.gitee.quite.system.filter.LoginByAccountFilter;
import com.gitee.quite.system.handler.AuthenticationJsonEntryPointHandler;
import com.gitee.quite.system.handler.ResultAccessDeniedHandler;
import com.gitee.quite.system.service.QuiteUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final QuiteUserService userService;
    
    private final LoginByAccountFilter loginByAccountFilter;
    
    private final ResultAccessDeniedHandler accessDeniedHandler;
    
    private final AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler;
    
    private final LogoutHandler logoutHandler;
    
    private final LogoutSuccessHandler logoutSuccessHandler;
    
    public SpringSecurityConfig(QuiteUserService userService, LoginByAccountFilter loginByAccountFilter,
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()
                .authorizeRequests()
                    .mvcMatchers(Url.REGISTER).permitAll()
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
        // @formatter:on
        http.addFilterAt(loginByAccountFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
