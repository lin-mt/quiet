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

package com.gitee.quiet.system.handler;

import com.gitee.quiet.common.constant.service.MessageSourceCode;
import com.gitee.quiet.service.config.MessageSourceConfig;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.MessageSourceUtil;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * 登陆/认证 失败 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultAuthenticationFailureHandler extends AbstractResponseJsonData
    implements AuthenticationFailureHandler {

    @Resource(name = MessageSourceConfig.QUIET_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        logger.error("用户登陆失败", exception);
        Result<Object> failure = Result.failure();
        failure.setCode(MessageSourceCode.Account.LOGIN_FAILURE);
        failure.setMessage(
            MessageSourceUtil.getMessage(request, messageSource, MessageSourceCode.Account.LOGIN_FAILURE));
        responseJsonData(response, failure);
    }
}
