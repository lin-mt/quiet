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
import com.gitee.quiet.common.service.enums.Gender;
import com.gitee.quiet.common.service.enums.Whether;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;

/**
 * QuietUserDetails.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class QuietUserDetails extends BaseEntity implements UserDetails, CredentialsContainer {
    
    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, length = 10)
    @NotEmpty(message = "{user.username}{not.empty}")
    @Length(max = 10, message = "{user.username}{length.max.limit}")
    private String username;
    
    /**
     * 全名（姓名）
     */
    @Column(name = "full_name", nullable = false, length = 10)
    @NotEmpty(message = "{user.fullName}{not.empty}")
    @Length(max = 10, message = "{user.fullName}{length.max.limit}")
    private String fullName;
    
    /**
     * 头像地址
     */
    @Column(name = "avatar", length = 100)
    private String avatar;
    
    /**
     * 密码
     */
    @Column(name = "secret_code", nullable = false, length = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{user.secretCode}{not.empty}")
    @Length(message = "{user.secretCode}{length.max.limit}", max = 30)
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
    @Email(message = "{user.email.address}")
    @Column(name = "email_address", length = 100)
    private String emailAddress;
    
    /**
     * 账号是否过期
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_expired", length = 3)
    private Whether accountExpired;
    
    /**
     * 账号是否被锁
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_locked", length = 3)
    private Whether accountLocked;
    
    /**
     * 密码是否过期
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "credentials_expired", length = 3)
    private Whether credentialsExpired;
    
    /**
     * 账号是否启用
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "enabled", length = 3)
    private Whether enabled;
    
    /**
     * 角色集合
     */
    @Transient
    private Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> authorities;
    
    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    @JsonIgnore
    public String getSecretCode() {
        return secretCode;
    }
    
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public Whether getAccountExpired() {
        return accountExpired;
    }
    
    public void setAccountExpired(Whether accountExpired) {
        this.accountExpired = accountExpired;
    }
    
    public Whether getAccountLocked() {
        return accountLocked;
    }
    
    public void setAccountLocked(Whether accountLocked) {
        this.accountLocked = accountLocked;
    }
    
    public Whether getCredentialsExpired() {
        return credentialsExpired;
    }
    
    public void setCredentialsExpired(Whether credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
    
    public Whether getEnabled() {
        return enabled;
    }
    
    @Override
    @Transient
    @JsonIgnore
    public String getPassword() {
        return getSecretCode();
    }
    
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return Whether.NO.equals(getAccountExpired());
    }
    
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return Whether.NO.equals(getAccountLocked());
    }
    
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return Whether.NO.equals(getCredentialsExpired());
    }
    
    @Override
    @Transient
    public boolean isEnabled() {
        return Whether.YES.equals(getEnabled());
    }
    
    public void setEnabled(Whether enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public void eraseCredentials() {
        this.secretCode = null;
    }
    
    @Override
    public Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>> authorities) {
        this.authorities = authorities;
    }
    
}