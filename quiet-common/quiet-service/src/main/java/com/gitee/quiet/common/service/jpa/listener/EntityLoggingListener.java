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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.service.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记录数据变化情况.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class EntityLoggingListener {
    
    /**
     * 日志格式：${前缀}:${表名}:${实体信息}
     */
    private static final String LOGGER_FORMAT = "{}:{}:{}";
    
    private static final String DEFAULT_PREFIX = "entityLog";
    
    private static final Map<Class<?>, LoggerCache> CLASS_LOGGER_MAP = new ConcurrentHashMap<>();
    
    private static volatile ObjectMapper objectMapper;
    
    @PrePersist
    public void prePersist(Object entity) {
        this.logger("prePersist", entity);
    }
    
    @PreUpdate
    public void preUpdate(Object entity) {
        this.logger("preUpdate", entity);
    }
    
    @PreRemove
    public void preRemove(Object entity) {
        this.logger("preRemove", entity);
    }
    
    @PostLoad
    public void postLoad(Object entity) {
        this.logger("postLoad", entity);
    }
    
    @PostRemove
    public void postRemove(Object entity) {
        this.logger("postRemove", entity);
    }
    
    @PostUpdate
    public void postUpdate(Object entity) {
        this.logger("postUpdate", entity);
    }
    
    @PostPersist
    public void postPersist(Object entity) {
        this.logger("postPersist", entity);
    }
    
    /**
     * 打印日志 todo 使用 消息队列/线程池 进行优化
     *
     * @param prefix 前缀
     * @param entity 打印的实体信息
     */
    private void logger(String prefix, Object entity) {
        if (entity == null) {
            return;
        }
        prefix = StringUtils.isBlank(prefix) ? DEFAULT_PREFIX : prefix;
        if (objectMapper == null) {
            synchronized (EntityLoggingListener.class) {
                if (objectMapper == null) {
                    objectMapper = ApplicationUtil.getBean(ObjectMapper.class);
                }
            }
        }
        LoggerCache cache = getLogger(entity.getClass());
        try {
            cache.getLogger()
                    .info(LOGGER_FORMAT, prefix, cache.getTableName(), objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
            cache.getLogger().error("{}：Json serialization failed when printing log.", prefix, e);
        }
    }
    
    private LoggerCache getLogger(Class<?> clazz) {
        return CLASS_LOGGER_MAP.computeIfAbsent(clazz, (key) -> {
            LoggerCache cache = new LoggerCache();
            cache.setLogger(LoggerFactory.getLogger(key));
            String tableName = null;
            Table table = AnnotationUtils.findAnnotation(key, Table.class);
            if (table != null) {
                tableName = table.name();
            }
            if (StringUtils.isBlank(tableName)) {
                Entity entity = AnnotationUtils.findAnnotation(key, Entity.class);
                if (entity != null) {
                    tableName = entity.name();
                }
            }
            if (StringUtils.isBlank(tableName)) {
                tableName = clazz.getSimpleName();
            }
            cache.setTableName(tableName);
            return cache;
        });
    }
    
    @Getter
    @Setter
    private static class LoggerCache {
        
        /**
         * 日志
         */
        private Logger logger;
        
        /**
         * 表格名称
         */
        private String tableName;
        
    }
    
}
