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
