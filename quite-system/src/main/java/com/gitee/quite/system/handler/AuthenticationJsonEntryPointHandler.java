package com.gitee.quite.system.handler;

import com.gitee.quite.system.config.ApplicationConfig;
import com.gitee.quite.system.constant.AccountCode;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.util.MessageUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录时返回 json 对象.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class AuthenticationJsonEntryPointHandler extends AbstractResponseJsonData implements AuthenticationEntryPoint {
    
    @Resource(name = ApplicationConfig.COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        logger.error("未登录");
        Result<Object> result = Result.failure().setCode(AccountCode.NO_LOGIN)
                .setMessage(MessageUtils.getMessage(request, messageSource, AccountCode.NO_LOGIN));
        responseJsonData(response, result);
    }
}
