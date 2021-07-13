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

package com.gitee.quiet.system.handler;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import com.gitee.quiet.common.service.config.MessageSourceConfig;
import com.gitee.quiet.common.service.util.CurrentUserUtil;
import com.gitee.quiet.system.constant.AccountCode;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权访问 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class ResultAccessDeniedHandler extends AbstractResponseJsonData implements AccessDeniedHandler {
    
    @Resource(name = MessageSourceConfig.QUIET_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        logger.error("用户：{} 无权限访问：{}", CurrentUserUtil.getId(), request.getRequestURI(), exception);
        Result<Object> failure = Result.failure();
        failure.setCode(AccountCode.NO_PERMISSION);
        failure.setMessage(MessageSourceUtil.getMessage(request, messageSource, AccountCode.NO_PERMISSION));
        responseJsonData(response, failure);
    }
}
