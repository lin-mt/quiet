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

package com.gitee.quiet.service.filter;

import com.gitee.quiet.service.filter.wrapper.ParameterNameWithSnakeCaseWrapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Get 请求方法添加参数的驼峰格式，同时不修改原有参数信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class GetMethodQueryParamSnakeCaseFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            filterChain.doFilter(new ParameterNameWithSnakeCaseWrapper(request), response);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
