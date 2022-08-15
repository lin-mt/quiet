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

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@AllArgsConstructor
public class QuietAccessDecisionManager implements AccessDecisionManager {

  private final RoleHierarchy roleHierarchy;

  @Override
  public void decide(
      Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    // 当接口未被配置资源时直接放行
    if (CollectionUtils.isEmpty(configAttributes)) {
      return;
    }
    for (GrantedAuthority authority :
        roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())) {
      for (ConfigAttribute configAttribute : configAttributes) {
        if (authority.getAuthority().trim().equalsIgnoreCase(configAttribute.getAttribute())) {
          return;
        }
      }
    }
    throw new AccessDeniedException("抱歉，您没有访问权限");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
