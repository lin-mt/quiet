package com.gitee.quite.system.entity;

import com.gitee.quite.common.validation.curd.base.Create;
import com.gitee.quite.common.validation.curd.base.Update;
import com.gitee.quite.system.base.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

/**
 * 角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_role")
public class QuiteRole extends BaseEntity implements GrantedAuthority {
    
    private Long parentId;
    
    @NotEmpty(groups = {Create.class, Update.class}, message = "{role.roleName}{not.empty}")
    private String roleName;
    
    @NotEmpty(groups = {Create.class, Update.class}, message = "{role.roleCnName}{not.empty}")
    private String roleCnName;
    
    private String remarks;
    
    @Override
    @Transient
    public String getAuthority() {
        return getRoleName();
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
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Basic
    @Column(name = "role_cn_name")
    public String getRoleCnName() {
        return roleCnName;
    }
    
    public void setRoleCnName(String roleCnName) {
        this.roleCnName = roleCnName;
    }
    
    @Basic
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}
