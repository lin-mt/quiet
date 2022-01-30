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

package com.gitee.quiet.service.json.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.gitee.quiet.common.constant.service.Url;
import com.gitee.quiet.service.json.annotation.JsonHasRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;

/**
 * json 序列化属性根据当前角色过滤.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class HasRoleAnnotationFilter extends SimpleBeanPropertyFilter {
    
    @Override
    public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
            throws Exception {
        boolean hasRolePermission = false;
        ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (request != null && Url.REGISTER.equals(request.getRequest().getRequestURI())) {
            return;
        }
        JsonHasRole jsonHasRole = writer.getAnnotation(JsonHasRole.class);
        if (jsonHasRole != null && StringUtils.isNoneBlank(jsonHasRole.value())) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (jsonHasRole.value().equals(authority.getAuthority())) {
                    hasRolePermission = true;
                    break;
                }
            }
        } else {
            hasRolePermission = true;
        }
        if (hasRolePermission) {
            super.serializeAsField(pojo, jgen, provider, writer);
        }
    }
    
}
