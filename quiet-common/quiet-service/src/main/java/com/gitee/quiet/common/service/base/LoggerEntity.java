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
