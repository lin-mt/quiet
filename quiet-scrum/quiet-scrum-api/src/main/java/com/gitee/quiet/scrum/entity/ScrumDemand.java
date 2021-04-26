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

import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.jpa.entity.ParentAndSerialEntity;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.gitee.quiet.scrum.entity.QScrumDemand.scrumDemand;

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
    @NotBlank
    @Length(max = 30)
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    
    /**
     * 需求类型
     */
    @NotNull
    @Column(name = "demand_type", nullable = false, length = 60)
    private Dictionary type;
    
    /**
     * 执行者
     */
    @Column(name = "executor_id", nullable = false)
    private Long executorId;
    
    /**
     * 项目ID
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    
    /**
     * 该需求所优化的需求ID，A需求优化了B需求，则A需求的optimizeDemandId为B需求的ID
     */
    @Column(name = "optimize_demand_id")
    private Long optimizeDemandId;
    
    /**
     * 所属迭代ID
     */
    @Column(name = "iteration_id")
    private Long iterationId;
    
    /**
     * 优先级ID
     */
    @NotNull
    @Column(name = "priority_id", nullable = false)
    private Long priorityId;
    
    /**
     * 需求开始时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    /**
     * 需求结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    /**
     * 备注信息
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
    
    public Dictionary getType() {
        return type;
    }
    
    public void setType(Dictionary type) {
        this.type = type;
    }
    
    public Long getExecutorId() {
        return executorId;
    }
    
    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }
    
    public Long getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public Long getOptimizeDemandId() {
        return optimizeDemandId;
    }
    
    public void setOptimizeDemandId(Long optimizeDemandId) {
        this.optimizeDemandId = optimizeDemandId;
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
    
    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), scrumDemand.id)
                .notNullEq(getType(), scrumDemand.type)
                .notBlankContains(getTitle(), scrumDemand.title)
                .notNullEq(getExecutorId(), scrumDemand.executorId)
                .notNullEq(getProjectId(), scrumDemand.projectId)
                .notNullEq(getOptimizeDemandId(), scrumDemand.optimizeDemandId)
                .notNullEq(getIterationId(), scrumDemand.iterationId)
                .notNullEq(getPriorityId(), scrumDemand.priorityId)
                .getPredicate();
        // @formatter:on
    }
}
