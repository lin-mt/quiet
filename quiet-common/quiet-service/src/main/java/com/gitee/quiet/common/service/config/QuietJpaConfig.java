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

package com.gitee.quiet.common.service.config;

import com.gitee.quiet.common.service.aware.QuietAuditorAware;
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
public class QuietJpaConfig {
    
    @PersistenceContext
    private final EntityManager entityManager;
    
    public QuietJpaConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    
    @Bean
    @ConditionalOnMissingBean(AuditorAware.class)
    public QuietAuditorAware quietAuditorAware() {
        return new QuietAuditorAware() {
        };
    }
    
}
