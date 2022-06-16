/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.jpa.listener;

import com.gitee.quiet.common.util.JsonUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

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

    private static final String DEFAULT_PREFIX = "entity-log";

    private static final Map<Class<?>, LoggerCache> CLASS_LOGGER_MAP = new ConcurrentHashMap<>();

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
        LoggerCache cache = getLogger(entity.getClass());
        cache.getLogger().info(LOGGER_FORMAT, prefix, cache.getTableName(), JsonUtils.toJsonString(entity));
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
