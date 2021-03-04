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
public class QuietGrantedAuthority<T extends QuietGrantedAuthority<T>> extends ParentEntity<T>
        implements GrantedAuthority {
    
    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false, length = 30)
    @NotEmpty(message = "{role.roleName}{not.empty}")
    @Length(max = 30, message = "{role.roleName}{length.max.limit}")
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
