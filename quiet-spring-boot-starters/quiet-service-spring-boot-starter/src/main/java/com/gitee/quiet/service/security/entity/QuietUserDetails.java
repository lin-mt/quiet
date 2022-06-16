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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.common.constant.service.RoleNames;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.service.enums.Gender;
import com.gitee.quiet.service.json.annotation.JsonHasRole;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * QuietUserDetails.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@MappedSuperclass
public class QuietUserDetails extends BaseEntity implements UserDetails, CredentialsContainer {

    /**
     * 用户名
     */
    @NotBlank
    @Length(max = 10)
    @Column(name = "username", nullable = false, length = 10)
    private String username;

    /**
     * 全名（姓名）
     */
    @NotBlank
    @Length(max = 10)
    @Column(name = "full_name", nullable = false, length = 10)
    private String fullName;

    /**
     * 头像地址
     */
    @Length(max = 100)
    @Column(name = "avatar", length = 100)
    private String avatar;

    /**
     * 密码
     */
    @NotBlank
    @Length(max = 60)
    @Column(name = "secret_code", nullable = false, length = 60)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String secretCode;

    /**
     * 性别
     */
    @Column(name = "gender", length = 1)
    private Gender gender;

    /**
     * 电话号码（手机号码）
     */
    @Pattern(regexp = "^1\\d{10}$", message = "{user.phoneNumber.wrong}")
    @Length(min = 11, max = 11, message = "{user.phoneNumber.wrong}")
    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    /**
     * 邮箱地址
     */
    @Email
    @Column(name = "email_address", length = 100)
    private String emailAddress;

    /**
     * 账号是否过期
     */
    @ColumnDefault("0")
    @JsonHasRole(RoleNames.Admin)
    @Column(name = "account_expired", columnDefinition = "TINYINT(1)")
    private Boolean accountExpired;

    /**
     * 账号是否被锁
     */
    @ColumnDefault("0")
    @JsonHasRole(RoleNames.Admin)
    @Column(name = "account_locked", columnDefinition = "TINYINT(1)")
    private Boolean accountLocked;

    /**
     * 密码是否过期
     */
    @ColumnDefault("0")
    @JsonHasRole(RoleNames.Admin)
    @Column(name = "credentials_expired", columnDefinition = "TINYINT(1)")
    private Boolean credentialsExpired;

    /**
     * 账号是否启用
     */
    @ColumnDefault("0")
    @JsonHasRole(RoleNames.Admin)
    @Column(name = "enabled", columnDefinition = "TINYINT(1)")
    private Boolean enabled;

    /**
     * 角色集合
     */
    @Transient
    @JsonHasRole(RoleNames.Admin)
    private Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> authorities;

    @Override
    @Transient
    @JsonIgnore
    public String getPassword() {
        return getSecretCode();
    }

    @Override
    @Transient
    @JsonHasRole(RoleNames.Admin)
    public boolean isAccountNonExpired() {
        return !BooleanUtils.toBoolean(getAccountExpired());
    }

    @Override
    @Transient
    @JsonHasRole(RoleNames.Admin)
    public boolean isAccountNonLocked() {
        return !BooleanUtils.toBoolean(getAccountLocked());
    }

    @Override
    @Transient
    @JsonHasRole(RoleNames.Admin)
    public boolean isCredentialsNonExpired() {
        return !BooleanUtils.toBoolean(getCredentialsExpired());
    }

    @Override
    public boolean isEnabled() {
        return BooleanUtils.toBoolean(getEnabled());
    }

    @Override
    public void eraseCredentials() {
        this.secretCode = null;
    }

    @JsonIgnore
    public String getSecretCode() {
        return secretCode;
    }
}
