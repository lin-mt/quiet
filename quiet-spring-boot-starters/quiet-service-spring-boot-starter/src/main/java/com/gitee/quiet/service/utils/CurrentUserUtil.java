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

package com.gitee.quiet.service.utils;

import com.gitee.quiet.common.constant.service.MessageSourceCode;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.service.security.entity.QuietGrantedAuthority;
import com.gitee.quiet.service.security.entity.QuietUserDetails;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CurrentUserUtil {

    private CurrentUserUtil() {
    }

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
        Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> authorities = get().getAuthorities();
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
