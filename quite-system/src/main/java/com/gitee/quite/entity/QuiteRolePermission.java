package com.gitee.quite.entity;

import com.gitee.quite.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 角色-权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_role_permission")
public class QuiteRolePermission extends BaseEntity {
    
    private Long roleId;
    
    private Long permissionId;
    
    @Basic
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    @Basic
    @Column(name = "permission_id")
    public Long getPermissionId() {
        return permissionId;
    }
    
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
    
}
