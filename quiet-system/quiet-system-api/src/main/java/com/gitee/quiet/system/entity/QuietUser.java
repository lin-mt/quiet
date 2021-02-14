/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quiet.common.service.base.BaseEntity;
import com.gitee.quiet.common.service.enums.Gender;
import com.gitee.quiet.common.service.enums.Whether;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_user")
public class QuietUser extends BaseEntity implements UserDetails, CredentialsContainer {
    
    // TODO 自定义序列化 Token 将参数提取到 BaseEnity
    @Id
    @Null(groups = Create.class, message = "id {null}")
    @NotNull(groups = Update.class, message = "id {not.null}")
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SnowFlakeIdGenerator")
    @GenericGenerator(name = "SnowFlakeIdGenerator", strategy = "com.gitee.quiet.common.service.id.SnowFlakeIdGenerator")
    private Long id;
    
    @CreatedBy
    @Column(name = "creator", updatable = false)
    private Long creator;
    
    @LastModifiedBy
    @Column(name = "updater", insertable = false)
    private Long updater;
    
    @CreatedDate
    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;
    
    @LastModifiedDate
    @Column(name = "gmt_update", insertable = false)
    private LocalDateTime gmtUpdate;
    
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public Long getCreator() {
        return creator;
    }
    
    @Override
    public void setCreator(Long createBy) {
        this.creator = createBy;
    }
    
    @Override
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }
    
    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
    @Override
    public Long getUpdater() {
        return updater;
    }
    
    @Override
    public void setUpdater(Long updateBy) {
        this.updater = updateBy;
    }
    
    @Override
    public LocalDateTime getGmtUpdate() {
        return gmtUpdate;
    }
    
    @Override
    public void setGmtUpdate(LocalDateTime gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
    
    /**
     * 用户名
     */
    @Column(name = "username")
    @NotEmpty(message = "{user.username}{not.empty}")
    @Length(max = 10, message = "{user.username.length}{length.max.limit}")
    private String username;
    
    /**
     * 头像地址
     */
    @Column(name = "avatar")
    private String avatar;
    
    /**
     * 密码
     */
    @Column(name = "secret_code")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{user.secretCode}{not.empty}")
    private String secretCode;
    
    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    /**
     * 电话号码（手机号码）
     */
    @Pattern(regexp = "^1\\d{10}$", message = "{user.phoneNumber.wrong}")
    @Length(min = 11, max = 11, message = "{user.phoneNumber.wrong}")
    @Column(name = "phone_number")
    private String phoneNumber;
    
    /**
     * 邮箱地址
     */
    @Email(message = "{user.email.address}")
    @Column(name = "email_address")
    private String emailAddress;
    
    /**
     * 账号是否过期
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_expired")
    private Whether accountExpired;
    
    /**
     * 账号是否被锁
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_locked")
    private Whether accountLocked;
    
    /**
     * 密码是否过期
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "credentials_expired")
    private Whether credentialsExpired;
    
    /**
     * 账号是否启用
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "enabled")
    private Whether enabled;
    
    /**
     * 角色集合
     */
    @Transient
    private Collection<QuietRole> authorities;
    
    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Collection<QuietRole> authorities) {
        this.authorities = authorities;
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
    public String toString() {
        return new ToStringBuilder(this).append("username", username).append("avatar", avatar)
                .append("secretCode", secretCode).append("gender", gender).append("phoneNumber", phoneNumber)
                .append("emailAddress", emailAddress).append("accountNonExpired", accountExpired)
                .append("accountNonLocked", accountLocked).append("credentialsNonExpired", credentialsExpired)
                .append("enabled", enabled).append("authorities", authorities).toString();
    }
}
