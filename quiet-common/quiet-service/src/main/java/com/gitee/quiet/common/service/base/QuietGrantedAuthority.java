package com.gitee.quiet.common.service.base;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

/**
 * QuietGrantedAuthority.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class QuietGrantedAuthority extends BaseEntity implements GrantedAuthority {
    
    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false, length = 30)
    @NotEmpty(message = "{role.roleName}{not.empty}")
    @Length(max = 30, message = "{role.roleName.length}{length.max.limit}")
    private String roleName;
    
    @Override
    @Transient
    public String getAuthority() {
        return getRoleName();
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
