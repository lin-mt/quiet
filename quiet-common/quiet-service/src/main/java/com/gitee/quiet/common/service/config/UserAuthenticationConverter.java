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

package com.gitee.quiet.common.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.service.jpa.entity.QuietUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ResourceAuthentication 和 ResourceServer之间的用户信息转换.
 * TODO update security
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings("deprecation")
public class UserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    
    public static final String QUIET_USER_DETAILS = "quiet_user_details";
    
    private final ObjectMapper objectMapper;
    
    public UserAuthenticationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        response.put(QUIET_USER_DETAILS, authentication.getPrincipal());
        return response;
    }
    
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(QUIET_USER_DETAILS)) {
            QuietUserDetails principal = objectMapper.convertValue(map.get(QUIET_USER_DETAILS), QuietUserDetails.class);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return super.extractAuthentication(map);
    }
}
