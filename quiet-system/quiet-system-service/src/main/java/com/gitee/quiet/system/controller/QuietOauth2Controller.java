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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证相关的Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/oauth")
public class QuietOauth2Controller {

    private final TokenStore tokenStore;

    public QuietOauth2Controller(@Qualifier("redisTokenStore") TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @PostMapping("/logout")
    public Result<Object> exist(@RequestParam("access_token") String accessToken) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        if (oAuth2AccessToken != null) {
            // 移除access_token
            tokenStore.removeAccessToken(oAuth2AccessToken);
            // 移除refresh_token
            if (oAuth2AccessToken.getRefreshToken() != null) {
                tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
            }
        }
        return Result.success();
    }

    @GetMapping("/oauth_user")
    public Principal oauthUser(Principal principal) {
        return principal;
    }

}
