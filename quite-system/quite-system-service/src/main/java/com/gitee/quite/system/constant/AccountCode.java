package com.gitee.quite.system.constant;

/**
 * 跟账号相关的通用 Code.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class AccountCode {
    
    public static final String PREFIX = "account.";
    
    public static final String NO_LOGIN = buildCode("no.login");
    
    public static final String LOGIN_SUCCESS = buildCode("login.success");
    
    public static final String LOGIN_FAILURE = buildCode("login.failure");
    
    public static final String LOGOUT_SUCCESS = buildCode("logout.success");
    
    public static final String NO_PERMISSION = buildCode("no.permission");
    
    private static String buildCode(String code) {
        return PREFIX + code;
    }
    
    private AccountCode() {
    }
}
