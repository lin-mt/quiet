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

import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import com.gitee.quiet.scrum.dictionary.TaskType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Length(max = 10)
    @Column(name = "title", nullable = false, length = 10)
    private String title;
    
    /**
     * 任务类型
     */
    @NotNull
    @Column(name = "task_type", nullable = false, length = 30)
    private Dictionary<TaskType> type;
    
    /**
     * 所属需求ID
     */
    @NotNull
    @Column(name = "demand_id", nullable = false)
    private Long demandId;
    
    /**
     * 任务的当前步骤ID
     */
    @NotNull
    @Column(name = "task_step_id", nullable = false)
    private Long taskStepId;
    
    /**
     * 执行者
     */
    @NotNull
    @Column(name = "executor_id", nullable = false)
    private Long executorId;
    
    /**
     * 参与者（最多20人参与）
     */
    @Size(max = 20)
    @Column(name = "participant", length = 380)
    private Set<Long> participant;
    
    /**
     * 前置任务
     */
    @Size(max = 20)
    @Column(name = "pre_task", length = 380)
    private Set<Long> preTaskIds;
    
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
    @Length(max = 3000)
    @Column(name = "remark", length = 3000)
    private String remark;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Dictionary<TaskType> getType() {
        return type;
    }
    
    public void setType(Dictionary<TaskType> type) {
        this.type = type;
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
    
    public Set<Long> getPreTaskIds() {
        return preTaskIds;
    }
    
    public void setPreTaskIds(Set<Long> preTaskIds) {
        this.preTaskIds = preTaskIds;
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
