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

package com.gitee.quiet.service.security;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 动态权限数据源，用于获取动态权限规则.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  private final UrlPermissionService urlPermissionService;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Value("${spring.application.name}")
  private String applicationName;

  private String rolePrefix = "ROLE_";

  public QuietSecurityMetadataSource(
      UrlPermissionService urlPermissionService,
      GrantedAuthorityDefaults grantedAuthorityDefaults) {
    this.urlPermissionService = urlPermissionService;
    if (grantedAuthorityDefaults != null) {
      rolePrefix = grantedAuthorityDefaults.getRolePrefix();
    }
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    List<UrlPermission> urlPermissions = urlPermissionService.listUrlPermission(applicationName);
    Set<ConfigAttribute> configAttributes = new HashSet<>();
    if (CollectionUtils.isEmpty(urlPermissions)) {
      return configAttributes;
    }
    String url = ((FilterInvocation) object).getRequestUrl();
    String method = ((FilterInvocation) object).getRequest().getMethod();
    for (UrlPermission urlPermission : urlPermissions) {
      if (pathMatcher.match(urlPermission.getUrlPattern(), url)) {
        if (StringUtils.isNotBlank(urlPermission.getRequestMethod())
            && !urlPermission.getRequestMethod().trim().equalsIgnoreCase(method)) {
          continue;
        }
        configAttributes.add((ConfigAttribute) () -> rolePrefix + urlPermission.getRoleName());
      }
    }
    return configAttributes;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
