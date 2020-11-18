/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.common.service.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 动态权限数据源，用于获取动态权限规则.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuiteSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    
    @Value("${spring.application.name}")
    private String applicationName;
    
    private final UrlPermissionService urlPermissionService;
    
    private final ConcurrentHashMap<String, Set<UrlPermission>> urlToConfigAttributes = new ConcurrentHashMap<>();
    
    private final AtomicBoolean init = new AtomicBoolean(false);
    
    private String rolePrefix = "ROLE_";
    
    public QuiteSecurityMetadataSource(UrlPermissionService urlPermissionService,
            GrantedAuthorityDefaults grantedAuthorityDefaults) {
        this.urlPermissionService = urlPermissionService;
        if (grantedAuthorityDefaults != null) {
            rolePrefix = grantedAuthorityDefaults.getRolePrefix();
        }
    }
    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (!init.get()) {
            synchronized (urlToConfigAttributes) {
                if (!init.get()) {
                    List<UrlPermission> urlPermissions = urlPermissionService.listUrlPermission(applicationName);
                    for (UrlPermission urlPermission : urlPermissions) {
                        if (!urlToConfigAttributes.containsKey(urlPermission.getUrlPattern())) {
                            urlToConfigAttributes.put(urlPermission.getUrlPattern(), new HashSet<>());
                        }
                        urlToConfigAttributes.get(urlPermission.getUrlPattern()).add(urlPermission);
                    }
                    init.set(true);
                }
            }
        }
        Set<ConfigAttribute> configAttributes = new HashSet<>();
        if (urlToConfigAttributes.isEmpty()) {
            return configAttributes;
        }
        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getRequest().getMethod();
        Iterator<String> iterator = urlToConfigAttributes.keys().asIterator();
        PathMatcher pathMatcher = new AntPathMatcher();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (pathMatcher.match(key, url)) {
                Set<UrlPermission> urlPermissions = urlToConfigAttributes.get(key);
                for (UrlPermission urlPermission : urlPermissions) {
                    if (StringUtils.isNotBlank(urlPermission.getRequestMethod()) && !urlPermission.getRequestMethod()
                            .trim().equalsIgnoreCase(method)) {
                        continue;
                    }
                    configAttributes.add((ConfigAttribute) () -> rolePrefix + urlPermission.getRoleName());
                }
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
