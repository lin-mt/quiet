package com.gitee.quite.system.handler;

import com.gitee.quite.system.config.ApplicationConfig;
import com.gitee.quite.system.constant.AccountCode;
import com.gitee.quite.system.result.Result;
import com.gitee.quite.system.util.MessageUtils;
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
    
    @Resource(name = ApplicationConfig.COMMON_MESSAGE_SOURCE)
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
