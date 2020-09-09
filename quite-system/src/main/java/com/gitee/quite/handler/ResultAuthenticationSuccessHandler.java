package com.gitee.quite.handler;

import com.gitee.quite.constant.ResultCode;
import com.gitee.linmt.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        logger.info("用户登陆成功：{}", authentication);
        Result<Object> result = Result.success().setCode(ResultCode.LOGIN_SUCCESS).setMessage("登陆成功");
        responseJsonData(response, result);
    }
}
