package com.gitee.quite.system.config;

import com.gitee.quite.common.service.aware.QuiteAuditorAware;
import com.gitee.quite.system.entity.QuiteUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Spring Data Jpa Config.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class SpringDataJpaConfig {
    
    @Bean
    public SpringSecurityAuditorAware springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }
    
    static class SpringSecurityAuditorAware implements QuiteAuditorAware {
        
        @NonNull
        @Override
        public Optional<Long> getCurrentAuditor() {
            // @formatter:off
            Optional<Object> principle = Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication)
                    .filter(Authentication::isAuthenticated)
                    .map(Authentication::getPrincipal);
            // @formatter:on
            Long id = null;
            if (principle.isPresent()) {
                if (principle.get() instanceof QuiteUser) {
                    QuiteUser user = (QuiteUser) principle.get();
                    id = user.getId();
                }
            }
            return Optional.ofNullable(id);
        }
    }
}
