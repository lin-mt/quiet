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

package com.gitee.quiet.service.filter.wrapper;

import com.gitee.quiet.common.util.StringConverterUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.iterators.IteratorEnumeration;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 添加参数驼峰格式，同时不改变原有的下划线的参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
public class ParameterNameToSnakeCaseWrapper extends HttpServletRequestWrapper {
    
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterNameToSnakeCaseWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> parameterNames = super.getParameterNames();
        Set<String> parameters = new HashSet<>();
        while (parameterNames != null && parameterNames.hasMoreElements()) {
            String param = parameterNames.nextElement();
            parameters.add(param);
            parameters.add(StringConverterUtil.lowerCamel(param));
        }
        return new IteratorEnumeration<>(parameters.iterator());
    }
    
    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(parameterValues)) {
            parameterValues = super.getParameterValues(StringConverterUtil.lowerUnderscore(name));
        }
        return parameterValues;
    }
    
    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if (StringUtils.isBlank(parameter)) {
            parameter = super.getParameter(StringConverterUtil.lowerUnderscore(name));
        }
        return parameter;
    }
    
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        if (MapUtils.isEmpty(parameterMap)) {
            return parameterMap;
        }
        Map<String, String[]> newParameterMap = new HashedMap<>(parameterMap.size() * 2);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            newParameterMap.put(entry.getKey(), entry.getValue());
            newParameterMap.put(StringConverterUtil.lowerCamel(entry.getKey()), entry.getValue());
        }
        return newParameterMap;
    }
}
