package com.gitee.quite.config;

import com.gitee.quite.util.ApplicationUtil;
import com.gitee.quite.util.SnowFlakeIdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class ApplicationConfig {
    
    @Value("${snow-flake-id-work.worker-id:0}")
    private Long workId;
    
    @Value("${snow-flake-id-work.data-center-id:0}")
    private Long dataCenterId;
    
    @Bean
    public ApplicationUtil applicationUtil() {
        return new ApplicationUtil();
    }
    
    @Bean
    public SnowFlakeIdWorker snowFlakeIdWorker() {
        return new SnowFlakeIdWorker(workId, dataCenterId);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
