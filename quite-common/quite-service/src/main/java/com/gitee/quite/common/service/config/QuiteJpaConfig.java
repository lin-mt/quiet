package com.gitee.quite.common.service.config;

import com.gitee.quite.common.service.aware.QuiteAuditorAware;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 所有服务的 Jpa 配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class QuiteJpaConfig {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
    public QuiteJpaConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
    @Bean
    @ConditionalOnMissingBean(AuditorAware.class)
    public QuiteAuditorAware quiteAuditorAware() {
        return new QuiteAuditorAware() {
        };
    }
    
}
