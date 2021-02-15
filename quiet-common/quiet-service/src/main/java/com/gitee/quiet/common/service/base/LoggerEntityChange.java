package com.gitee.quiet.common.service.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.io.Serializable;

/**
 * TODO 记录数据变化情况.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class LoggerEntityChange implements Serializable {
    
    public final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @PrePersist
    public void prePersist() {
        LOGGER.info("prePersist:{}", this);
    }
    
    @PreUpdate
    public void preUpdate() {
        LOGGER.info("preUpdate:{}", this);
    }
    
    @PreRemove
    public void preRemove() {
        LOGGER.info("preRemove:{}", this);
    }
    
    @PostLoad
    public void postLoad() {
        LOGGER.info("postLoad:{}", this);
    }
    
    @PostRemove
    public void postRemove() {
        LOGGER.info("postRemove:{}", this);
    }
    
    @PostUpdate
    public void postUpdate() {
        LOGGER.info("postUpdate:{}", this);
    }
    
    @PostPersist
    public void postPersist() {
        LOGGER.info("postPersist:{}", this);
    }
}
