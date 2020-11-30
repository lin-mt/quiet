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

package com.gitee.quite.system.entity;

import com.gitee.quite.common.service.base.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_permission")
public class QuitePermission extends BaseEntity {
    
    /**
     * 应用名称
     */
    @NotEmpty(message = "{permission.applicationName}{not.empty}")
    private String applicationName;
    
    /**
     * URL 匹配规则
     */
    @NotEmpty(message = "{permission.urlPattern}{not.empty}")
    @Length(max = 100, message = "{permission.urlPattern.length}{length.max.limit}")
    private String urlPattern;
    
    /**
     * 请求方法
     */
    private String requestMethod;
    
    /**
     * 角色ID
     */
    @NotNull(message = "{permission.roleId}{not.null}")
    private Long roleId;
    
    /**
     * 备注
     */
    @Length(max = 100, message = "{permission.remark}{length.max.limit}")
    private String remark;
    
    @Basic
    @Column(name = "application_name")
    public String getApplicationName() {
        return applicationName;
    }
    
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    
    @Basic
    @Column(name = "url_pattern")
    public String getUrlPattern() {
        return urlPattern;
    }
    
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
    
    @Basic
    @Column(name = "request_method")
    public String getRequestMethod() {
        return requestMethod;
    }
    
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    
    @Basic
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String description) {
        this.remark = description;
    }
}
