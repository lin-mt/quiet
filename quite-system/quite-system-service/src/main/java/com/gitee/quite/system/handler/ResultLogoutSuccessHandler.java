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

package com.gitee.quite.system.handler;

import com.gitee.quite.common.service.config.QuiteServiceConfig;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.service.util.MessageUtils;
import com.gitee.quite.system.constant.AccountCode;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出成功返回 json 数据.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultLogoutSuccessHandler extends AbstractResponseJsonData implements LogoutSuccessHandler {
    
    @Resource(name = QuiteServiceConfig.QUITE_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        logger.info("用户退出登录成功：{}", authentication);
        Result<Object> result = Result.success().setCode(AccountCode.LOGOUT_SUCCESS)
                .setMessage(MessageUtils.getMessage(request, messageSource, AccountCode.LOGOUT_SUCCESS));
        responseJsonData(response, result);
    }
}
