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

package com.gitee.quiet.common.service.jpa.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录数据变化情况.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class EntityLoggingListener {
    
    private static final Map<Class<?>, Logger> CLASS_LOGGER_MAP = new ConcurrentHashMap<>();
    
    @PrePersist
    public void prePersist(Object entity) {
        this.getLogger(entity.getClass()).info("prePersist:{}", entity);
    }
    
    @PreUpdate
    public void preUpdate(Object entity) {
        this.getLogger(entity.getClass()).info("preUpdate:{}", entity);
    }
    
    @PreRemove
    public void preRemove(Object entity) {
        this.getLogger(entity.getClass()).info("preRemove:{}", entity);
    }
    
    @PostLoad
    public void postLoad(Object entity) {
        this.getLogger(entity.getClass()).info("postLoad:{}", entity);
    }
    
    @PostRemove
    public void postRemove(Object entity) {
        this.getLogger(entity.getClass()).info("postRemove:{}", entity);
    }
    
    @PostUpdate
    public void postUpdate(Object entity) {
        this.getLogger(entity.getClass()).info("postUpdate:{}", entity);
    }
    
    @PostPersist
    public void postPersist(Object entity) {
        this.getLogger(entity.getClass()).info("postPersist:{}", entity);
    }
    
    private Logger getLogger(Class<?> clazz) {
        return CLASS_LOGGER_MAP.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }
    
}
