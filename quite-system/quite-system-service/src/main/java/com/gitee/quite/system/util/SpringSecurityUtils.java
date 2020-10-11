package com.gitee.quite.system.util;

import com.gitee.quite.system.entity.QuiteUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class SpringSecurityUtils {
    
    private SpringSecurityUtils() {
    }
    
    /**
     * 获取当前登录人信息.
     *
     * @return 登录人.
     */
    public static QuiteUser getCurrentUser() {
        return ((QuiteUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
    
    /**
     * 获取当前登录人用户名.
     *
     * @return 当前登录人用户名
     */
    public static String getCurrentUserUsername() {
        QuiteUser user = getCurrentUser();
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }
    
    /**
     * 获取当前登录人ID.
     *
     * @return 当前登录人ID
     */
    public static Long getCurrentUserId() {
        QuiteUser user = getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return null;
    }
}
