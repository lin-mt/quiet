/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.service.security.filter;

import com.gitee.quiet.service.security.QuietAccessDecisionManager;
import com.gitee.quiet.service.security.QuietSecurityMetadataSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Url 过滤.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
public class QuietUrlSecurityFilter extends AbstractSecurityInterceptor implements Filter {

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
      filterInvocation
          .getChain()
          .doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
      return;
    }
    // 此处会调用AccessDecisionManager中的decide方法进行鉴权操作
    InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
    try {
      filterInvocation
          .getChain()
          .doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
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
