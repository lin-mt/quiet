package com.gitee.quite.system.config;

import com.gitee.quite.system.entity.QuiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Optional;

/**
 * Spring Data Jpa Config.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class SpringDataJpaConfig {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
    public SpringDataJpaConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
    @Bean
    public SpringSecurityAuditorAware springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }
    
    static class SpringSecurityAuditorAware implements AuditorAware<Long> {
        
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
        
        // TODO 以下可以做操作数据的日志记录，可以替代逻辑删除的功能
        
        @PrePersist
        public void prePersist(Object o) {
        
        }
        
        @PreUpdate
        public void preUpdate(Object o) {
        
        }
        
        @PreRemove
        public void preRemove(Object o) {
        
        }
        
        @PostLoad
        public void postLoad(Object o) {
        
        }
        
        @PostRemove
        public void postRemove(Object o) {
        
        }
        
        @PostUpdate
        public void postUpdate(Object o) {
        
        }
        
        @PostPersist
        public void postPersist(Object o) {
        
        }
    }
}
