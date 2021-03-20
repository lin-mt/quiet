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

import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 任务信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_task")
public class ScrumTask extends SerialEntity {
    
    /**
     * 任务标题
     */
    @Column(name = "title", nullable = false, length = 10)
    @Length(message = "{task.title}{length.max.limit}", max = 10)
    @NotNull(message = "{task.title}{not.null}", groups = {Create.class, Update.class})
    @NotEmpty(message = "{task.title}{not.empty}", groups = {Create.class, Update.class})
    private String title;
    
    /**
     * 所属需求ID
     */
    @Column(name = "demand_id", nullable = false)
    @NotNull(message = "{task.demandId}{not.null}", groups = {Create.class, Update.class})
    private Long demandId;
    
    /**
     * 任务的当前步骤ID
     */
    @Column(name = "task_step_id", nullable = false)
    @NotNull(message = "{task.taskStepId}{not.null}", groups = {Create.class, Update.class})
    private Long taskStepId;
    
    /**
     * 执行者
     */
    @Column(name = "executor_id", nullable = false)
    @NotNull(message = "{task.executorId}{not.null}", groups = {Create.class, Update.class})
    private Long executorId;
    
    /**
     * 任务备注信息
     */
    @Column(name = "remark", length = 3000)
    @Length(message = "{task.remark}{length.max.limit}", max = 3000)
    private String remark;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Long getDemandId() {
        return demandId;
    }
    
    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }
    
    public Long getTaskStepId() {
        return taskStepId;
    }
    
    public void setTaskStepId(Long taskStepId) {
        this.taskStepId = taskStepId;
    }
    
    public Long getExecutorId() {
        return executorId;
    }
    
    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
