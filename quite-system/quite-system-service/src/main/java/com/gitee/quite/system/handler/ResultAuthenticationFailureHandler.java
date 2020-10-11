package com.gitee.quite.system.handler;

import com.gitee.quite.common.service.config.QuiteServiceConfig;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.service.util.MessageUtils;
import com.gitee.quite.system.constant.AccountCode;
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
    
    @Resource(name = QuiteServiceConfig.QUITE_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        logger.error("用户登陆失败", exception);
        Result<Object> result = Result.failure().setCode(AccountCode.LOGIN_FAILURE)
                .setMessage(MessageUtils.getMessage(request, messageSource, AccountCode.LOGIN_FAILURE));
        responseJsonData(response, result);
    }
}
