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
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
     * 模板名称
     */
    @Column(name = "template_name", nullable = false, length = 10)
    @Length(message = "{template.name}{length.max.limit}", max = 10)
    @NotNull(message = "{template.name}{not.null}")
    @NotEmpty(message = "{template.name}{not.empty}")
    private String name;
    
    /**
     * 模板中的任务步骤
     */
    @Transient
    List<ScrumTaskStep> taskSteps;
    
    /**
     * 模板备注信息
     */
    @Column(name = "remark", length = 30)
    @Length(message = "{template.remark}{length.max.limit}", max = 30)
    private String remark;
    
    /**
     * 是否启用，true：项目可以选择该模板，false：项目新建的时候不可以选择该模块
     */
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean enable;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        this.enable = enable;
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
}