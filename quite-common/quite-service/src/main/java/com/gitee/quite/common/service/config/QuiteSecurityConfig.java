package com.gitee.quite.common.service.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * .
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class QuiteSecurityConfig extends GlobalMethodSecurityConfiguration {

}
