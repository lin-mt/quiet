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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.gitee.quiet.system.entity.QQuietPermission.quietPermission;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_permission")
public class QuietPermission extends BaseEntity {
    
    /**
     * 应用名称
     */
    @NotBlank
    @Length(max = 100)
    @Column(name = "application_name", nullable = false, length = 100)
    private String applicationName;
    
    /**
     * URL 匹配规则
     */
    @NotBlank
    @Length(max = 100)
    @Column(name = "url_pattern", length = 100)
    private String urlPattern;
    
    /**
     * 请求方法
     */
    @NotBlank
    @Length(max = 7)
    @Column(name = "request_method", length = 7)
    private String requestMethod;
    
    /**
     * 角色ID
     */
    @NotNull
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    
    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;
    
    public String getApplicationName() {
        return applicationName;
    }
    
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    
    public String getUrlPattern() {
        return urlPattern;
    }
    
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
    
    public String getRequestMethod() {
        return requestMethod;
    }
    
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String description) {
        this.remark = description;
    }
    
    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), quietPermission.id)
                .notNullEq(getRoleId(), quietPermission.roleId)
                .notBlankEq(getRequestMethod(), quietPermission.requestMethod)
                .notBlankContains(getApplicationName(), quietPermission.applicationName)
                .notBlankContains(getUrlPattern(), quietPermission.urlPattern)
                .notBlankContains(getRemark(), quietPermission.remark)
                .getPredicate();
        // @formatter:on
    }
}
