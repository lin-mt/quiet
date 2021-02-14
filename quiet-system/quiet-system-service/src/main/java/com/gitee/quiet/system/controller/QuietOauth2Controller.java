package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
}
