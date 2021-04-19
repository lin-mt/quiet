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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.common.service.jpa.entity.ParentAndSerialEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 项目的版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_version")
public class ScrumVersion extends ParentAndSerialEntity<ScrumVersion> {
    
    /**
     * 版本名称
     */
    @Column(name = "version_name", nullable = false, length = 10)
    @Length(message = "{version.name}{length.max.limit}", max = 10)
    @NotNull(message = "{version.name}{not.null}")
    @NotEmpty(message = "{version.name}{not.empty}")
    private String name;
    
    /**
     * 所属项目ID
     */
    @Column(name = "project_id", nullable = false)
    @NotNull(message = "{version.projectId}{not.null}")
    private Long projectId;
    
    /**
     * 版本备注信息
     */
    @Column(name = "remark", nullable = false, length = 1500)
    @Length(message = "{version.remark}{length.max.limit}", max = 1500)
    @NotNull(message = "{version.remark}{not.null}")
    @NotEmpty(message = "{version.remark}{not.empty}")
    private String remark;
    
    /**
     * 迭代信息
     */
    @Transient
    private List<ScrumIteration> iterations;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<ScrumIteration> getIterations() {
        return iterations;
    }
    
    public void setIterations(List<ScrumIteration> iterations) {
        this.iterations = iterations;
    }
}