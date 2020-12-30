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

import com.gitee.quiet.system.constant.AccountCode;
import com.gitee.quiet.common.service.config.QuietServiceConfig;
import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆/认证 失败 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultAuthenticationFailureHandler extends AbstractResponseJsonData
        implements AuthenticationFailureHandler {
    
    @Resource(name = QuietServiceConfig.QUIET_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        logger.error("用户登陆失败", exception);
        Result<Object> result = Result.failure().setCode(AccountCode.LOGIN_FAILURE)
                .setMessage(MessageSourceUtil.getMessage(request, messageSource, AccountCode.LOGIN_FAILURE));
        responseJsonData(response, result);
    }
}
