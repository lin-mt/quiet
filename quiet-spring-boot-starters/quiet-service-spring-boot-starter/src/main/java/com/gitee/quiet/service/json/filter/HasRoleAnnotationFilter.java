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
  public void serializeAsField(
      Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
      throws Exception {
    boolean hasRolePermission = false;
    ServletRequestAttributes request =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (request != null && Url.REGISTER.equals(request.getRequest().getRequestURI())) {
      return;
    }
    JsonHasRole jsonHasRole = writer.getAnnotation(JsonHasRole.class);
    if (jsonHasRole != null && StringUtils.isNoneBlank(jsonHasRole.value())) {
      Collection<? extends GrantedAuthority> authorities =
          SecurityContextHolder.getContext().getAuthentication().getAuthorities();
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
