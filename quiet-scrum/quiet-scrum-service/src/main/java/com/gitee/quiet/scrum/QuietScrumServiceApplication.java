package com.gitee.quiet.scrum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@EnableJpaRepositories(basePackages = "com.gitee.quiet.scrum.repository")
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class QuietScrumServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuietScrumServiceApplication.class, args);
    }
    
}
