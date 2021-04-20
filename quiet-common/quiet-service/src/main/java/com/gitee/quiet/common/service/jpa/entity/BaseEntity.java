/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.common.service.jpa.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.gitee.quiet.common.service.jpa.listener.EntityLoggingListener;
import com.gitee.quiet.common.service.json.filter.JsonFilterName;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类的公共属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@JsonFilter(JsonFilterName.HAS_ROLE)
@EntityListeners({AuditingEntityListener.class, EntityLoggingListener.class})
public class BaseEntity implements Serializable {
    
    @Id
    @Null(groups = Create.class, message = "id {null}")
    @NotNull(groups = Update.class, message = "id {not.null}")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SnowFlakeIdGenerator")
    @GenericGenerator(name = "SnowFlakeIdGenerator", strategy = "com.gitee.quiet.common.service.id.SnowFlakeIdGenerator")
    private Long id;
    
    @CreatedBy
    @Column(name = "creator", updatable = false)
    private Long creator;
    
    @LastModifiedBy
    @Column(name = "updater", insertable = false)
    private Long updater;
    
    @CreatedDate
    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;
    
    @LastModifiedDate
    @Column(name = "gmt_update", insertable = false)
    private LocalDateTime gmtUpdate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCreator() {
        return creator;
    }
    
    public void setCreator(Long createBy) {
        this.creator = createBy;
    }
    
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }
    
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
    public Long getUpdater() {
        return updater;
    }
    
    public void setUpdater(Long updateBy) {
        this.updater = updateBy;
    }
    
    public LocalDateTime getGmtUpdate() {
        return gmtUpdate;
    }
    
    public void setGmtUpdate(LocalDateTime gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
    
    @Nullable
    public BooleanBuilder booleanBuilder() {
        return null;
    }
}
