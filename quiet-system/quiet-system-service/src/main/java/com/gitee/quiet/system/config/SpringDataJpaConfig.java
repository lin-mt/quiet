/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.system.config;

import com.gitee.quiet.common.service.aware.QuietAuditorAware;
import com.gitee.quiet.system.entity.QuietUser;
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
    
    static class SpringSecurityAuditorAware implements QuietAuditorAware {
        
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
                if (principle.get() instanceof QuietUser) {
                    QuietUser user = (QuietUser) principle.get();
                    id = user.getId();
                }
            }
            return Optional.ofNullable(id);
        }
    }
}