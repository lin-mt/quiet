/*
 * Copyright $.today.year lin-mt@outlook.com
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

import com.gitee.quiet.common.service.base.Serial;
import com.gitee.quiet.common.service.jpa.entity.ParentAndSerialEntity;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @NotBlank
    @Length(max = 10)
    @Column(name = "version_name", nullable = false, length = 10)
    private String name;
    
    /**
     * 所属项目ID
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    
    /**
     * 计划开始日期
     */
    @NotNull
    @Column(name = "plan_start_date", nullable = false)
    private LocalDate planStartDate;
    
    /**
     * 计划结束日期
     */
    @NotNull
    @Column(name = "plan_end_date", nullable = false)
    private LocalDate planEndDate;
    
    /**
     * 版本开始时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    /**
     * 版本结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;
    
    /**
     * 版本备注信息
     */
    @NotBlank
    @Length(max = 1500)
    @Column(name = "remark", nullable = false, length = 1500)
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
    
    public LocalDate getPlanStartDate() {
        return planStartDate;
    }
    
    public void setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
    }
    
    public LocalDate getPlanEndDate() {
        return planEndDate;
    }
    
    public void setPlanEndDate(LocalDate planEndDate) {
        this.planEndDate = planEndDate;
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
    
    public List<ScrumIteration> getIterations() {
        return iterations;
    }
    
    public void setIterations(List<ScrumIteration> iterations) {
        this.iterations = iterations;
    }
    
    @Override
    public String getTitle() {
        return getName();
    }
    
    @Override
    public int compareTo(@Nullable Serial other) {
        int compare = super.compareTo(other);
        if (compare == 0 && other instanceof ScrumVersion) {
            ScrumVersion otherVersion = (ScrumVersion) other;
            compare = planStartDate.compareTo(otherVersion.getPlanStartDate());
            if (compare == 0) {
                compare = getGmtCreate().compareTo(otherVersion.getGmtCreate());
            }
        }
        return compare;
    }
}
