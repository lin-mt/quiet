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

package com.gitee.quiet.service.security.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.service.security.entity.QuietUserDetails;
import lombok.AllArgsConstructor;
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
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
@SuppressWarnings("deprecation")
public class UserAuthenticationConverter extends DefaultUserAuthenticationConverter {

  public static final String QUIET_USER_DETAILS = "quiet_user_details";

  private final ObjectMapper objectMapper;

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
      QuietUserDetails principal =
          objectMapper.convertValue(map.get(QUIET_USER_DETAILS), QuietUserDetails.class);
      Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
      return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
    }
    return super.extractAuthentication(map);
  }
}
