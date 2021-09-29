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

package com.gitee.quiet.jpa.entity.base;

import com.gitee.quiet.jpa.listener.EntityLoggingListener;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, EntityLoggingListener.class})
public class BaseEntity implements Serializable {
    
    @Id
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
    @GenericGenerator(name = "IdGenerator", strategy = "com.gitee.quiet.jpa.id.IdGenerator")
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
    
    @Nullable
    public BooleanBuilder booleanBuilder() {
        return null;
    }
}