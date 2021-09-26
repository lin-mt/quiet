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
    
    public QuietSecurityMetadataSource(UrlPermissionService urlPermissionService,
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
                if (StringUtils.isNotBlank(urlPermission.getRequestMethod()) && !urlPermission.getRequestMethod().trim()
                        .equalsIgnoreCase(method)) {
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
