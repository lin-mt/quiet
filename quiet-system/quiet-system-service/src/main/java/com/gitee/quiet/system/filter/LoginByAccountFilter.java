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

package com.gitee.quiet.system.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.constant.service.Url;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.handler.ResultAuthenticationFailureHandler;
import com.gitee.quiet.system.handler.ResultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理使用 Json 格式数据的登陆方式.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class LoginByAccountFilter extends UsernamePasswordAuthenticationFilter {
    
    private final ObjectMapper objectMapper;
    
    @Autowired
    public LoginByAccountFilter(ResultAuthenticationSuccessHandler authenticationSuccessHandler,
            ResultAuthenticationFailureHandler authenticationFailureHandler,
            @Lazy AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        // 自定义该方式处理登录信息的登录地址，默认是 /login POST
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(Url.LOGIN_BY_ACCOUNT, "POST"));
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }
    
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        if (null != request.getContentType() && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            UsernamePasswordAuthenticationToken authToken = null;
            QuietUser user;
            try (final InputStream inputStream = request.getInputStream()) {
                user = objectMapper.readValue(inputStream, QuietUser.class);
                authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            } catch (final IOException e) {
                logger.error(e);
                authToken = new UsernamePasswordAuthenticationToken("", "");
                throw new AuthenticationServiceException("Failed to read data from request", e.getCause());
            } finally {
                setDetails(request, authToken);
            }
            // 进行登录信息的验证
            return this.getAuthenticationManager().authenticate(authToken);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
    
}
