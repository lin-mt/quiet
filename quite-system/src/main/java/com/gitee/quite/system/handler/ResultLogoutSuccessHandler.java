package com.gitee.quite.system.handler;

import com.gitee.quite.system.constant.ResultCode;
import com.gitee.linmt.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

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
    
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        logger.info("用户退出登录成功：{}", authentication);
        Result<Object> result = Result.success().setCode(ResultCode.LOGOUT_SUCCESS).setMessage("退出成功");
        responseJsonData(response, result);
    }
}
