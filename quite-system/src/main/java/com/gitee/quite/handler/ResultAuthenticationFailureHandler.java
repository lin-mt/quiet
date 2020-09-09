package com.gitee.quite.handler;

import com.gitee.quite.constant.ResultCode;
import com.gitee.linmt.entity.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

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
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        logger.error("用户登陆失败", exception);
        Result<Object> result = Result.failure().setCode(ResultCode.LOGIN_FAILURE).setMessage("用户名或密码错误");
        responseJsonData(response, result);
    }
}
