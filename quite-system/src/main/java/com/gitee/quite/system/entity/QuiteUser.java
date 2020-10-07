package com.gitee.quite.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gitee.quite.system.base.BaseEntity;
import com.gitee.quite.system.enums.Gender;
import com.gitee.quite.system.enums.Whether;
import com.gitee.quite.system.validation.group.curd.base.Create;
import com.gitee.quite.system.validation.group.curd.base.Update;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

/**
 * 用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_user")
public class QuiteUser extends BaseEntity implements UserDetails, CredentialsContainer {
    
    @Column(name = "username")
    @NotEmpty(groups = {Create.class, Update.class}, message = "{user.username}{not.empty}")
    private String username;
    
    @Column(name = "avatar")
    private String avatar;
    
    @Column(name = "secret_code")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(groups = {Create.class, Update.class}, message = "{user.secretCode}{not.empty}")
    private String secretCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email_address")
    private String emailAddress;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_non_expired")
    private Whether accountNonExpired;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_non_locked")
    private Whether accountNonLocked;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "credentials_non_expired")
    private Whether credentialsNonExpired;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "enabled")
    private Whether enabled;
    
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    
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
    
    public Whether getAccountNonExpired() {
        return accountNonExpired;
    }
    
    public Whether getAccountNonLocked() {
        return accountNonLocked;
    }
    
    public Whether getCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    public Whether getEnabled() {
        return enabled;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
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
        return Whether.YES.equals(getAccountNonExpired());
    }
    
    public void setAccountNonExpired(Whether accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return Whether.YES.equals(getAccountNonLocked());
    }
    
    public void setAccountNonLocked(Whether accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return Whether.YES.equals(getCredentialsNonExpired());
    }
    
    public void setCredentialsNonExpired(Whether credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
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
                .append("emailAddress", emailAddress).append("accountNonExpired", accountNonExpired)
                .append("accountNonLocked", accountNonLocked).append("credentialsNonExpired", credentialsNonExpired)
                .append("enabled", enabled).append("authorities", authorities).toString();
    }
}
