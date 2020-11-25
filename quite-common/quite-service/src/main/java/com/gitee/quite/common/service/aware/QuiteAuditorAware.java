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

package com.gitee.quite.common.service.aware;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

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
public interface QuiteAuditorAware extends AuditorAware<Long> {
    
    @NonNull
    @Override
    default Optional<Long> getCurrentAuditor() {
        // TODO 设置创建者和更新者信息
        return Optional.empty();
    }
    
    // TODO 以下可以做操作数据的日志记录，可以替代逻辑删除的功能
    
    @PrePersist
    default void prePersist(Object o) {
    
    }
    
    @PreUpdate
    default void preUpdate(Object o) {
    
    }
    
    @PreRemove
    default void preRemove(Object o) {
    
    }
    
    @PostLoad
    default void postLoad(Object o) {
    
    }
    
    @PostRemove
    default void postRemove(Object o) {
    
    }
    
    @PostUpdate
    default void postUpdate(Object o) {
    
    }
    
    @PostPersist
    default void postPersist(Object o) {
    
    }
}
