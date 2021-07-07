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

import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import com.gitee.quiet.system.entity.QuietUser;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * 项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_project")
public class DocProject extends SerialEntity {
    
    /**
     * 项目名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "project_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 项目文档负责人
     */
    @NotNull
    @Column(name = "principal", nullable = false)
    private Long principal;
    
    /**
     * 访问者用户ID
     */
    @Size(max = 30)
    @Column(name = "visitor_id", length = 570)
    private Set<Long> visitorIds;
    
    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;
    
    /**
     * 访问者信息
     */
    @Transient
    private List<QuietUser> visitors;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(Long principal) {
        this.principal = principal;
    }
    
    public Set<Long> getVisitorIds() {
        return visitorIds;
    }
    
    public void setVisitorIds(Set<Long> visitorIds) {
        this.visitorIds = visitorIds;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<QuietUser> getVisitors() {
        return visitors;
    }
    
    public void setVisitors(List<QuietUser> visitors) {
        this.visitors = visitors;
    }
}
