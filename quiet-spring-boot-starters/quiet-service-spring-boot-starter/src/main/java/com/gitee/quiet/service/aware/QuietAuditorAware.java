/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.service.aware;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * JpaAuditorAware.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietAuditorAware implements AuditorAware<Long> {
    
    /**
     * @return 操作者的 ID
     */
    @NonNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        // 获取创建者和更新者信息
        // @formatter:off
        Optional<Object> principle = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
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
    
}
