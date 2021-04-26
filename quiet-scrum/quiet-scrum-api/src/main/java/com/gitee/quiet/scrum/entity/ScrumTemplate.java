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

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 项目模板.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_template")
public class ScrumTemplate extends BaseEntity {
    
    /**
     * 模板中的任务步骤
     */
    @Transient
    List<ScrumTaskStep> taskSteps;
    
    /**
     * 模板名称
     */
    @NotBlank
    @Length(max = 10)
    @Column(name = "template_name", nullable = false, length = 10)
    private String name;
    
    /**
     * 是否启用，true：项目可以选择该模板，false：项目新建的时候不可以选择该模块
     */
    @ColumnDefault("0")
    @Column(name = "enabled", columnDefinition = "TINYINT(1)")
    private Boolean enabled;
    
    /**
     * 模板备注信息
     */
    @Length(max = 30)
    @Column(name = "remark", length = 30)
    private String remark;
    
    /**
     * 模板中的优先级配置
     */
    @Transient
    List<ScrumPriority> priorities;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<ScrumTaskStep> getTaskSteps() {
        return taskSteps;
    }
    
    public void setTaskSteps(List<ScrumTaskStep> taskSteps) {
        this.taskSteps = taskSteps;
    }
    
    public List<ScrumPriority> getPriorities() {
        return priorities;
    }
    
    public void setPriorities(List<ScrumPriority> priorities) {
        this.priorities = priorities;
    }
}
