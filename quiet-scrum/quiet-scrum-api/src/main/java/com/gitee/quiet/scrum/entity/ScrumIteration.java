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
 * 迭代信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_iteration")
public class ScrumIteration extends SerialEntity {
    
    /**
     * 迭代名称
     */
    @Column(name = "iteration_name", nullable = false, length = 30)
    @Length(message = "{iteration.name}{length.max.limit}", max = 30)
    @NotNull(message = "{iteration.name}{not.null}", groups = {Create.class, Update.class})
    @NotEmpty(message = "{iteration.name}{not.empty}", groups = {Create.class, Update.class})
    private String name;
    
    /**
     * 所属版本ID
     */
    @Column(name = "version_id", nullable = false)
    @NotNull(message = "{iteration.versionId}{not.null}", groups = {Create.class, Update.class})
    private Long versionId;
    
    /**
     * 备注信息
     */
    @Column(name = "remark", nullable = false, length = 1000)
    @Length(message = "{iteration.remark}{length.max.limit}", max = 1000)
    private String remark;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getVersionId() {
        return versionId;
    }
    
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
