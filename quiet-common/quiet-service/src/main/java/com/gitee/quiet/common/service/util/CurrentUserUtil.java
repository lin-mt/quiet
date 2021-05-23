/*
 * Copyright $.today.year lin-mt@outlook.com
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

import com.gitee.quiet.common.base.constant.CommonCode;
import com.gitee.quiet.common.service.jpa.entity.QuietUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;

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
            throw new IllegalStateException(CommonCode.buildCode("account.no.login"));
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
}
