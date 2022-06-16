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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登陆/认证 成功 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultAuthenticationSuccessHandler extends AbstractResponseJsonData
    implements AuthenticationSuccessHandler {

    @Resource(name = MessageSourceConfig.QUIET_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        logger.info("用户登录成功：{}", authentication);
        Result<Object> success = Result.success();
        success.setCode(MessageSourceCode.Account.LOGIN_SUCCESS);
        success.setMessage(
            MessageSourceUtil.getMessage(request, messageSource, MessageSourceCode.Account.LOGIN_SUCCESS));
        responseJsonData(response, success);
    }
}
