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

package com.gitee.quiet.common.service.extend;

import com.gitee.quiet.common.service.util.ApplicationUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.validation.constraints.NotBlank;

/**
 * 对 SecurityContext 的扩展，从请求中获取 tokenValue，并从 redis 中获取认证信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietSecurityContext implements SecurityContext {
    
    private final String tokenValue;
    
    private Authentication authentication;
    
    public QuietSecurityContext(@NotBlank String tokenValue) {
        this.tokenValue = tokenValue;
    }
    
    @Override
    public Authentication getAuthentication() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (!(securityContext instanceof QuietSecurityContext)) {
            if (securityContext.getAuthentication() != null) {
                return securityContext.getAuthentication();
            }
        }
        if (authentication != null) {
            return authentication;
        }
        TokenStore tokenStore = ApplicationUtil.getBean(TokenStore.class);
        this.setAuthentication(tokenStore.readAuthentication(tokenValue));
        return authentication;
    }
    
    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
