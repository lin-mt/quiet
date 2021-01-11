/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 部门成员信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_department_user")
public class QuietDepartmentUser extends BaseEntity {
    
    /**
     * 部门ID
     */
    @NotNull(message = "{departmentUser.departmentId}{not.null}")
    private Long departmentId;
    
    /**
     * 用户ID
     */
    @NotNull(message = "{departmentUser.userId}{not.null}")
    private Long userId;
    
    public QuietDepartmentUser(@NotNull(message = "{departmentUser.departmentId}{not.null}") Long departmentId,
            @NotNull(message = "{departmentUser.userId}{not.null}") Long userId) {
        this.departmentId = departmentId;
        this.userId = userId;
    }
    
    public QuietDepartmentUser() {
    }
    
    @Basic
    @Column(name = "department_id")
    public Long getDepartmentId() {
        return departmentId;
    }
    
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
