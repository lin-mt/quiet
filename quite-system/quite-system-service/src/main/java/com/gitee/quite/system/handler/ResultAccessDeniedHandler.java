package com.gitee.quite.system.handler;

import com.gitee.quite.common.service.config.QuiteServiceConfig;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.service.util.MessageUtils;
import com.gitee.quite.system.constant.AccountCode;
import com.gitee.quite.system.util.SpringSecurityUtils;
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
    
    @Resource(name = QuiteServiceConfig.QUITE_COMMON_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        logger.error("用户：{} 无权限访问：{}", SpringSecurityUtils.getCurrentUserId(), request.getRequestURI(), exception);
        Result<Object> result = Result.failure().setCode(AccountCode.NO_PERMISSION)
                .setMessage(MessageUtils.getMessage(request, messageSource, AccountCode.NO_PERMISSION));
        responseJsonData(response, result);
    }
}
