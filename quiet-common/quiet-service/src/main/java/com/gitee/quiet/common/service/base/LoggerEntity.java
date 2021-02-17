package com.gitee.quiet.common.service.base;

import com.gitee.quiet.common.service.util.ApplicationUtil;
import com.gitee.quiet.common.service.util.EntityJsonLoggerUtil;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.io.Serializable;

/**
 * 记录数据变化情况.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class LoggerEntity implements Serializable {
    
    @PrePersist
    public void prePersist() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).prePersist(getClass(), this);
    }
    
    @PreUpdate
    public void preUpdate() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).preUpdate(getClass(), this);
    }
    
    @PreRemove
    public void preRemove() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).preRemove(getClass(), this);
    }
    
    @PostLoad
    public void postLoad() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).postLoad(getClass(), this);
    }
    
    @PostRemove
    public void postRemove() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).postRemove(getClass(), this);
    }
    
    @PostUpdate
    public void postUpdate() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).postUpdate(getClass(), this);
    }
    
    @PostPersist
    public void postPersist() {
        ApplicationUtil.getBean(EntityJsonLoggerUtil.class).postPersist(getClass(), this);
    }
}
