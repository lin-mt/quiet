package com.gitee.quite.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quite.constant.Url;
import com.gitee.quite.entity.QuiteUser;
import com.gitee.quite.handler.ResultAuthenticationFailureHandler;
import com.gitee.quite.handler.ResultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理使用 Json 格式数据的登陆方式.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class LoginByAccountFilter extends UsernamePasswordAuthenticationFilter {
    
    private final ObjectMapper objectMapper;
    
    @Autowired
    public LoginByAccountFilter(ResultAuthenticationSuccessHandler authenticationSuccessHandler,
            ResultAuthenticationFailureHandler authenticationFailureHandler,
            @Lazy AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        // 自定义该方式处理登录信息的登录地址，默认是 /login POST
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(Url.LOGIN_BY_ACCOUNT, "POST"));
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }
    
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        if (null != request.getContentType() && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            UsernamePasswordAuthenticationToken authToken = null;
            QuiteUser user;
            try (final InputStream inputStream = request.getInputStream()) {
                user = objectMapper.readValue(inputStream, QuiteUser.class);
                authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            } catch (final IOException e) {
                logger.error(e);
                authToken = new UsernamePasswordAuthenticationToken("", "");
                throw new AuthenticationServiceException("Failed to read data from request", e.getCause());
            } finally {
                setDetails(request, authToken);
            }
            // 进行登录信息的验证
            return this.getAuthenticationManager().authenticate(authToken);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
    
}
