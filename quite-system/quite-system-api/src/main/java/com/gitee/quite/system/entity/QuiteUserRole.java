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
 * 用户-角色.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_user_role")
public class QuiteUserRole extends BaseEntity {
    
    @NotNull(groups = {Create.class, Update.class}, message = "{userRole.userId}{not.null}")
    private Long userId;
    
    @NotNull(groups = {Create.class, Update.class}, message = "{userRole.roleId}{not.null}")
    private Long roleId;
    
    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Basic
    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
}
