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
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

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
    @NotNull(message = "{task.title}{not.null}")
    @NotEmpty(message = "{task.title}{not.empty}")
    private String title;
    
    /**
     * 所属需求ID
     */
    @Column(name = "demand_id", nullable = false)
    @NotNull(message = "{task.demandId}{not.null}")
    private Long demandId;
    
    /**
     * 任务的当前步骤ID
     */
    @Column(name = "task_step_id", nullable = false)
    @NotNull(message = "{task.taskStepId}{not.null}")
    private Long taskStepId;
    
    /**
     * 执行者
     */
    @Column(name = "executor_id", nullable = false)
    @NotNull(message = "{task.executorId}{not.null}")
    private Long executorId;
    
    /**
     * 参与者（最多20人参与）
     */
    @Column(name = "participant", length = 380)
    @Size(message = "{task.participant}{size.max.limit}", max = 20)
    private Set<Long> participant;
    
    /**
     * 前置任务
     */
    @Column(name = "pre_task", length = 380)
    @Size(message = "{task.preTask}{size.max.limit}", max = 20)
    private Set<Long> preTask;
    
    /**
     * 任务开始时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    /**
     * 任务结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
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
    
    public Set<Long> getParticipant() {
        return participant;
    }
    
    public void setParticipant(Set<Long> participant) {
        this.participant = participant;
    }
    
    public Set<Long> getPreTask() {
        return preTask;
    }
    
    public void setPreTask(Set<Long> preTask) {
        this.preTask = preTask;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
