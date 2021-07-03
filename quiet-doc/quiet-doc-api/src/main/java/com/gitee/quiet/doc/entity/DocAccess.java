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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.gitee.quiet.doc.enums.AccessType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文档访问信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_access")
public class DocAccess extends BaseEntity {
    
    /**
     * 访问者ID
     */
    @Column(name = "accessor", nullable = false)
    private Long accessor;
    
    /**
     * 可访问的信息类型
     */
    @Column(name = "access_type", nullable = false)
    private AccessType type;
    
    /**
     * 资源ID
     */
    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
    
    public Long getAccessor() {
        return accessor;
    }
    
    public void setAccessor(Long accessor) {
        this.accessor = accessor;
    }
    
    public AccessType getType() {
        return type;
    }
    
    public void setType(AccessType type) {
        this.type = type;
    }
    
    public Long getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
