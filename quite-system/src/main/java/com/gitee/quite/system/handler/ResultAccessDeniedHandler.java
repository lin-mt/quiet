package com.gitee.quite.system.handler;

import com.gitee.quite.system.constant.ResultCode;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.util.SpringSecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

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
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        logger.error("用户：{} 无权限访问：{}", SpringSecurityUtils.getCurrentUserId(), request.getRequestURI(), exception);
        Result<Object> result = Result.failure().setCode(ResultCode.NO_PERMISSION).setMessage("无权限访问");
        responseJsonData(response, result);
    }
}
