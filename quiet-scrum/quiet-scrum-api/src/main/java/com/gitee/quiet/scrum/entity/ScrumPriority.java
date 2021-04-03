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

/**
 * 优先级.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_priority")
public class ScrumPriority extends SerialEntity {
    
    /**
     * 优先级名称
     */
    @Column(name = "priority_name", nullable = false, length = 10)
    @Length(message = "{priority.name}{length.max.limit}", max = 10)
    @NotNull(message = "{priority.name}{not.null}")
    @NotEmpty(message = "{priority.name}{not.empty}")
    private String name;
    
    /**
     * 图标的十六进制颜色
     */
    @Column(name = "color_hex", length = 7)
    @Length(message = "{priority.colorHex}{length.max.limit}", max = 7)
    private String colorHex;
    
    /**
     * 模板ID
     */
    @Column(name = "template_id", nullable = false)
    @NotNull(message = "{priority.templateId}{not.null}")
    private Long templateId;
    
    /**
     * 备注信息
     */
    @Column(name = "remark", length = 100)
    @Length(message = "{priority.remark}{length.max.limit}", max = 100)
    private String remark;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getColorHex() {
        return colorHex;
    }
    
    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
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
