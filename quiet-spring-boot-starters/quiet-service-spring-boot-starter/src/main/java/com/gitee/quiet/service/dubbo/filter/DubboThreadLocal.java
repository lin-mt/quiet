package com.gitee.quiet.service.dubbo.filter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DubboThreadLocal {

    public static final ThreadLocal<String> USER_TOKEN = ThreadLocal.withInitial(() -> "");

}
