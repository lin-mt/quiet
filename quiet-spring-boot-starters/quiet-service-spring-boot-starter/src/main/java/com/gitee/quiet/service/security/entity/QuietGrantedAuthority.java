/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.service.security.entity;

import com.gitee.quiet.jpa.entity.ParentEntity;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

/**
 * QuietGrantedAuthority.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@MappedSuperclass
public class QuietGrantedAuthority<T extends QuietGrantedAuthority<T>> extends ParentEntity<T>
    implements GrantedAuthority {

    /**
     * 角色名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "role_name", nullable = false, length = 30)
    private String roleName;

    @Override
    @Transient
    public String getAuthority() {
        return getRoleName();
    }
}
