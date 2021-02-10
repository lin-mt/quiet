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

package com.gitee.quiet.common.service.aware;

import com.gitee.quiet.common.service.base.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Optional;

/**
 * JpaAuditorAware.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietAuditorAware implements AuditorAware<Long> {
    
    private final Logger logger = LoggerFactory.getLogger(QuietAuditorAware.class);
    
    /**
     * @return 操作者的 ID
     */
    @NonNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        // 获取创建者和更新者信息
        // @formatter:off
        Optional<Object> principle = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal);
        // @formatter:on
        Long id = null;
        if (principle.isPresent()) {
            Object currentUser = principle.get();
            if (currentUser instanceof BaseEntity) {
                id = ((BaseEntity) currentUser).getId();
            }
        }
        return Optional.ofNullable(id);
    }
    
    @PrePersist
    public void prePersist(Object o) {
        logger.info("prePersist:{}", o);
    }
    
    @PreUpdate
    public void preUpdate(Object o) {
        logger.info("preUpdate:{}", o);
    }
    
    @PreRemove
    public void preRemove(Object o) {
        logger.info("preRemove:{}", o);
    }
    
    @PostLoad
    public void postLoad(Object o) {
        logger.info("postLoad:{}", o);
    }
    
    @PostRemove
    public void postRemove(Object o) {
        logger.info("postRemove:{}", o);
    }
    
    @PostUpdate
    public void postUpdate(Object o) {
        logger.info("postUpdate:{}", o);
    }
    
    @PostPersist
    public void postPersist(Object o) {
        logger.info("postPersist:{}", o);
    }
    
}
