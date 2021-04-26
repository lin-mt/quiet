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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务步骤.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_task_step")
public class ScrumTaskStep extends SerialEntity {
    
    /**
     * 步骤名称
     */
    @NotBlank
    @Length(max = 10)
    @Column(name = "step_name", nullable = false, length = 10)
    private String name;
    
    /**
     * 所属模板ID
     */
    @NotNull
    @Column(name = "template_id", nullable = false)
    private Long templateId;
    
    /**
     * 步骤备注信息
     */
    @Length(max = 30)
    @Column(name = "remark", length = 30)
    private String remark;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
