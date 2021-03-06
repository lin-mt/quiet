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

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.List;

/**
 * 带有父子关系且有优先级信息的实体.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class ParentAndSerialEntity<T extends ParentAndSerialEntity<T>> extends BaseEntity implements Parent<T>, Serial {
    
    /**
     * 序号
     */
    @Column(name = "serial_number")
    private int serialNumber;
    
    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    
    @Transient
    private List<T> children;
    
    @Override
    public int getSerialNumber() {
        return serialNumber;
    }
    
    @Override
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    @Override
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    @Override
    public List<T> getChildren() {
        return children;
    }
    
    @Override
    public void setChildren(List<T> children) {
        this.children = children;
    }
}
