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

package com.gitee.quiet.service.security.context;

import com.gitee.quiet.service.utils.SpringUtil;
import javax.validation.constraints.NotBlank;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 对 SecurityContext 的扩展，从请求中获取 tokenValue，并从 redis 中获取认证信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietSecurityContext implements SecurityContext {

    private final String tokenValue;

    private final SecurityContext securityContext = SecurityContextHolder.getContext();

    public QuietSecurityContext(@NotBlank String tokenValue) {
        this.tokenValue = tokenValue;
    }

    @Override
    public Authentication getAuthentication() {
        if (securityContext.getAuthentication() != null) {
            return securityContext.getAuthentication();
        }
        TokenStore tokenStore = SpringUtil.getBean(TokenStore.class);
        OAuth2Authentication authentication = tokenStore.readAuthentication(tokenValue);
        this.setAuthentication(authentication);
        return securityContext.getAuthentication();
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        securityContext.setAuthentication(authentication);
    }

}
