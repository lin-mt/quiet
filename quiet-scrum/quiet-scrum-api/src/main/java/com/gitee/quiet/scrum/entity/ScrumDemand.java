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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.common.service.base.ParentAndSerialEntity;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 需求信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_demand")
public class ScrumDemand extends ParentAndSerialEntity<ScrumDemand> {
    
    /**
     * 需求标题
     */
    @Column(name = "title", nullable = false, length = 30)
    @Length(message = "{demand.title}{length.max.limit}", max = 30)
    @NotNull(message = "{demand.title}{not.null}", groups = {Create.class, Update.class})
    @NotEmpty(message = "{demand.title}{not.empty}", groups = {Create.class, Update.class})
    private String title;
    
    /**
     * 项目ID
     */
    @Column(name = "project_id", nullable = false)
    @NotNull(message = "{demand.projectId}{not.null}", groups = {Create.class, Update.class})
    private Long projectId;
    
    /**
     * 所属迭代ID
     */
    @Column(name = "iteration_id")
    private Long iterationId;
    
    /**
     * 优先级ID
     */
    @Column(name = "priority_id", nullable = false)
    @NotNull(message = "{demand.priorityId}{not.null}", groups = {Create.class, Update.class})
    private Long priorityId;
    
    /**
     * 备注信息
     */
    @Column(name = "remark", length = 3000)
    @Length(message = "{demand.remark}{length.max.limit}", max = 3000)
    private String remark;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Long getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public Long getIterationId() {
        return iterationId;
    }
    
    public void setIterationId(Long iterationId) {
        this.iterationId = iterationId;
    }
    
    public Long getPriorityId() {
        return priorityId;
    }
    
    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
