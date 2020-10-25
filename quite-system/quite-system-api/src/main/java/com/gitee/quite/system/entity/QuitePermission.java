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
import com.gitee.quite.system.validation.annotation.QuitePermissionCheck;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_permission")
@QuitePermissionCheck
public class QuitePermission extends BaseEntity {
    
    @NotEmpty(message = "{permission.applicationName}{not.empty}")
    private String applicationName;
    
    @NotEmpty(message = "{permission.urlPattern}{not.empty}")
    private String urlPattern;
    
    private String preFilterValue;
    
    private String preFilterFilterTarget;
    
    private String preAuthorizeValue;
    
    private String postFilterValue;
    
    private String postAuthorizeValue;
    
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
    @Column(name = "pre_filter_value")
    public String getPreFilterValue() {
        return preFilterValue;
    }
    
    public void setPreFilterValue(String preFilterValue) {
        this.preFilterValue = preFilterValue;
    }
    
    @Basic
    @Column(name = "pre_filter_filter_target")
    public String getPreFilterFilterTarget() {
        return preFilterFilterTarget;
    }
    
    public void setPreFilterFilterTarget(String preFilterFilterTarget) {
        this.preFilterFilterTarget = preFilterFilterTarget;
    }
    
    @Basic
    @Column(name = "pre_authorize_value")
    public String getPreAuthorizeValue() {
        return preAuthorizeValue;
    }
    
    public void setPreAuthorizeValue(String preAuthorizeValue) {
        this.preAuthorizeValue = preAuthorizeValue;
    }
    
    @Basic
    @Column(name = "post_filter_value")
    public String getPostFilterValue() {
        return postFilterValue;
    }
    
    public void setPostFilterValue(String postFilterValue) {
        this.postFilterValue = postFilterValue;
    }
    
    @Basic
    @Column(name = "post_authorize_value")
    public String getPostAuthorizeValue() {
        return postAuthorizeValue;
    }
    
    public void setPostAuthorizeValue(String postAuthorizeValue) {
        this.postAuthorizeValue = postAuthorizeValue;
    }
    
}
