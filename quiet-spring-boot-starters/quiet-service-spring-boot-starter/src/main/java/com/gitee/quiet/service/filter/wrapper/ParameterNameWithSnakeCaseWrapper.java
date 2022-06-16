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
