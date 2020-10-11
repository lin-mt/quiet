package com.gitee.quite.system.entity;

import com.gitee.quite.common.service.base.BaseEntity;
import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 角色-权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_role_permission")
public class QuiteRolePermission extends BaseEntity {
    
    @NotNull(groups = {Create.class, Update.class}, message = "{rolePermission.roleId}{not.null}")
    private Long roleId;
    
    @NotNull(groups = {Create.class, Update.class}, message = "{rolePermission.permissionId}{not.null}")
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
