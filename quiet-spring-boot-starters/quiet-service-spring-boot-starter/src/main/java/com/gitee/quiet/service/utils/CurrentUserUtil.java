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

package com.gitee.quiet.service.utils;

import com.gitee.quiet.common.constant.service.MessageSourceCode;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.service.security.entity.QuietGrantedAuthority;
import com.gitee.quiet.service.security.entity.QuietUserDetails;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Spring Security 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CurrentUserUtil {

  private CurrentUserUtil() {}

  /**
   * 获取当前登录人信息.
   *
   * @return 登录人.
   */
  @NotNull
  public static QuietUserDetails get() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new IllegalStateException(MessageSourceCode.Account.NO_LOGIN);
    }
    return (QuietUserDetails) authentication.getPrincipal();
  }

  /**
   * 获取当前登录人ID.
   *
   * @return 当前登录人ID
   */
  @NotNull
  public static Long getId() {
    return get().getId();
  }

  /**
   * 获取当前登录人用户名.
   *
   * @return 当前登录人用户名
   */
  @NotNull
  public static String getUsername() {
    return get().getUsername();
  }

  /**
   * 获取当前登录人全名.
   *
   * @return 当前登录人全名
   */
  @NotNull
  public static String getFullName() {
    return get().getFullName();
  }

  /**
   * 判断是否拥有某个角色的权限
   *
   * @param roleName 角色名称
   */
  public static boolean hasRole(String roleName) {
    Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> authorities =
        get().getAuthorities();
    if (CollectionUtils.isNotEmpty(authorities)) {
      for (QuietGrantedAuthority<? extends QuietGrantedAuthority<?>> authority : authorities) {
        if (roleName.equals(authority.getRoleName())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是否管理员
   *
   * @return true：是管理员、false：不是管理员
   */
  public static boolean isAdmin() {
    return hasRole(RoleNames.Admin) || isSystemAdmin();
  }

  /**
   * 是否系统管理员
   *
   * @return true：是系统管理员、false：不是系统管理员
   */
  public static boolean isSystemAdmin() {
    return hasRole(RoleNames.SystemAdmin);
  }
}
