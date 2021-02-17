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
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_role")
public class QuietRole extends BaseEntity implements GrantedAuthority {
    
    /**
     * 父角色ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    
    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false, length = 30)
    @NotEmpty(message = "{role.roleName}{not.empty}")
    @Length(max = 30, message = "{role.roleName.length}{length.max.limit}")
    private String roleName;
    
    /**
     * 角色中文名
     */
    @Column(name = "role_cn_name", nullable = false, length = 30)
    @NotEmpty(message = "{role.roleCnName}{not.empty}")
    @Length(max = 30, message = "{role.roleCnName.length}{length.max.limit}")
    private String roleCnName;
    
    /**
     * 备注
     */
    @Column(name = "remark", length = 100)
    @Length(max = 100, message = "{role.remark}{length.max.limit}")
    private String remark;
    
    /**
     * 父角色名称
     */
    @Transient
    private String parentRoleName;
    
    /**
     * 子角色
     */
    @Transient
    private List<QuietRole> children;
    
    @Override
    @Transient
    public String getAuthority() {
        return getRoleName();
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleCnName() {
        return roleCnName;
    }
    
    public void setRoleCnName(String roleCnName) {
        this.roleCnName = roleCnName;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remarks) {
        this.remark = remarks;
    }
    
    public String getParentRoleName() {
        return parentRoleName;
    }
    
    public void setParentRoleName(String parentRoleName) {
        this.parentRoleName = parentRoleName;
    }
    
    public List<QuietRole> getChildren() {
        return children;
    }
    
    public void setChildren(List<QuietRole> children) {
        this.children = children;
    }
    
    public void addChildren(QuietRole role) {
        if (CollectionUtils.isEmpty(getChildren())) {
            setChildren(new ArrayList<>());
        }
        getChildren().add(role);
    }
}
