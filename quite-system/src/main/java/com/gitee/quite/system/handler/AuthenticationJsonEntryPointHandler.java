package com.gitee.quite.system.handler;

import com.gitee.quite.system.constant.ResultCode;
import com.gitee.quite.system.result.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        logger.error("未登录");
        Result<Object> result = Result.failure().setCode(ResultCode.NO_LOGIN).setMessage("未登录，请重新登录！");
        responseJsonData(response, result);
    }
}
