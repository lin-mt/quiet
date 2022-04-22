/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.service.security.filter;

import com.gitee.quiet.service.security.QuietAccessDecisionManager;
import com.gitee.quiet.service.security.QuietSecurityMetadataSource;
import com.gitee.quiet.service.security.properties.QuietSecurityProperties;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * Url 过滤.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
public class QuietUrlSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    private final QuietSecurityProperties quietSecurityProperties;

    private final QuietSecurityMetadataSource quietSecurityMetadataSource;

    @Autowired
    public void setAccessDecisionManager(QuietAccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        if (HttpMethod.OPTIONS.matches(servletRequest.getMethod())) {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        if (CollectionUtils.isNotEmpty(quietSecurityProperties.getIgnoreUrls())) {
            for (String path : quietSecurityProperties.getIgnoreUrls()) {
                if (pathMatcher.match(path, servletRequest.getRequestURI())) {
                    filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                    return;
                }
            }
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return quietSecurityMetadataSource;
    }
}
