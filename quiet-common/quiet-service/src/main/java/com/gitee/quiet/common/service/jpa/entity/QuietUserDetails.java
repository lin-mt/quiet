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

package com.gitee.quiet.common.service.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.common.base.constant.RoleNames;
import com.gitee.quiet.common.service.enums.Gender;
import com.gitee.quiet.common.service.json.annotation.JsonHasRole;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 6)
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
    
}
