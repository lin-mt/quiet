/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.common.service.util;

import com.gitee.quiet.common.service.jpa.entity.QuietUserDetails;
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
    public static QuietUserDetails get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (QuietUserDetails) authentication.getPrincipal();
        }
        return null;
    }
    
    /**
     * 获取当前登录人ID.
     *
     * @return 当前登录人ID
     */
    public static Long getId() {
        QuietUserDetails user = get();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
    
    /**
     * 获取当前登录人用户名.
     *
     * @return 当前登录人用户名
     */
    public static String getUsername() {
        QuietUserDetails user = get();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }
    
    /**
     * 获取当前登录人全名.
     *
     * @return 当前登录人全名
     */
    public static String getFullName() {
        QuietUserDetails user = get();
        if (user != null) {
            return user.getFullName();
        }
        return null;
    }
}