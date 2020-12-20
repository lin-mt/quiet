/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.base.BaseEntity;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_department")
public class QuietDepartment extends BaseEntity {
    
    /**
     * 部门名称
     */
    @NotEmpty(groups = {Create.class, Update.class}, message = "{department.departmentName}{not.empty}")
    @Length(max = 10, message = "{department.departmentName.length}{length.max.limit}")
    private String departmentName;
    
    /**
     * 父级部门 ID
     */
    private Long parentId;
    
    /**
     * 备注
     */
    @Length(max = 100, message = "{department.remark}{length.max.limit}")
    private String remark;
    
    /**
     * 子部门信息
     */
    @Transient
    private List<QuietDepartment> children;
    
    @Basic
    @Column(name = "department_name")
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    @Basic
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public List<QuietDepartment> getChildren() {
        return children;
    }
    
    public void setChildren(List<QuietDepartment> children) {
        this.children = children;
    }
    
    public void addChildren(QuietDepartment children) {
        if (CollectionUtils.isEmpty(this.getChildren())) {
            this.setChildren(new ArrayList<>());
        }
        this.getChildren().add(children);
    }
}