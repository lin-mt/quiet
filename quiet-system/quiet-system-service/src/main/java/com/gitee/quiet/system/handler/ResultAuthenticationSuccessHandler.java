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

package com.gitee.quiet.system.handler;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import com.gitee.quiet.common.service.config.QuietMessageSourceConfig;
import com.gitee.quiet.system.constant.AccountCode;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆/认证 成功 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultAuthenticationSuccessHandler extends AbstractResponseJsonData
        implements AuthenticationSuccessHandler {
    
    @Resource(name = QuietMessageSourceConfig.QUIET_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        logger.info("用户登录成功：{}", authentication);
        Result<Object> result = Result.success().setCode(AccountCode.LOGIN_SUCCESS)
                .setMessage(MessageSourceUtil.getMessage(request, messageSource, AccountCode.LOGIN_SUCCESS));
        responseJsonData(response, result);
    }
}
