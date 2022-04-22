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
import com.google.common.collect.Maps;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 添加参数驼峰格式，同时不改变原有的下划线的参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ParameterNameWithSnakeCaseWrapper extends HttpServletRequestWrapper {

    private final Enumeration<String> parameterNames;

    private final Map<String, String[]> parameterMap;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterNameWithSnakeCaseWrapper(HttpServletRequest request) {
        super(request);
        Map<String, String[]> superParameterMap = super.getParameterMap();
        Vector<String> parameterNamesDatum = new Vector<>();
        Map<String, String[]> parameterMapDatum = Maps.newHashMap();
        String parameterName;
        String parameterNameLowerCamel;
        for (Map.Entry<String, String[]> entry : superParameterMap.entrySet()) {
            parameterName = entry.getKey();
            parameterNameLowerCamel = StringConverterUtil.lowerCamel(parameterName);
            parameterNamesDatum.add(parameterName);
            parameterMapDatum.put(parameterName, entry.getValue());
            parameterNamesDatum.add(parameterNameLowerCamel);
            parameterMapDatum.put(parameterNameLowerCamel, entry.getValue());
        }
        parameterNames = parameterNamesDatum.elements();
        parameterMap = Maps.newHashMap(parameterMapDatum);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.parameterNames;
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameterMap.get(name);
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameterMap.get(name);
        if (ArrayUtils.isNotEmpty(values)) {
            return values[0];
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }
}
