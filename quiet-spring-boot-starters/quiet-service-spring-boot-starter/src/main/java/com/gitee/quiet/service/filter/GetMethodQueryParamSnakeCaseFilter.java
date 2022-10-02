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

package com.gitee.quiet.service.filter;

import com.gitee.quiet.service.filter.wrapper.ParameterNameWithSnakeCaseWrapper;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Get 请求方法添加参数的驼峰格式，同时不修改原有参数信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class GetMethodQueryParamSnakeCaseFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    if (HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())
        || HttpMethod.DELETE.name().equalsIgnoreCase(request.getMethod())
        || HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
      filterChain.doFilter(new ParameterNameWithSnakeCaseWrapper(request), response);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
