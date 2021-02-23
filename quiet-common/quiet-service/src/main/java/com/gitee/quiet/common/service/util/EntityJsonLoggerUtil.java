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

package com.gitee.quiet.common.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.service.base.LoggerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体打印日志工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class EntityJsonLoggerUtil {
    
    private final ConcurrentHashMap<Class<?>, Logger> loggers = new ConcurrentHashMap<>();
    
    private final ObjectMapper objectMapper;
    
    public EntityJsonLoggerUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    public void prePersist(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("prePersist: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void preUpdate(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("preUpdate: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void preRemove(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("preRemove: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void postLoad(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("postLoad: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void postRemove(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("postRemove: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void postUpdate(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("postUpdate: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
    
    public void postPersist(Class<?> clazz, LoggerEntity entity) {
        try {
            loggers.computeIfAbsent(clazz, LoggerFactory::getLogger)
                    .info("postPersist: {}", objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException ignored) {
        }
    }
}
