package cn.linmt.quiet.auth.entity;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import com.google.common.collect.Lists;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "auth_user")
public class User extends QuietEntity implements UserDetails {

  /** 用户名称 */
  @NotBlank
  @Length(min = 2, max = 16)
  @Column(name = "username", nullable = false, length = 16)
  private String username;

  /** 密码 */
  @NotBlank
  @Length(max = 60)
  @Column(name = "password", nullable = false, length = 60)
  private String password;

  /** 头像 */
  @Length(max = 100)
  @Column(name = "avatar", length = 100)
  private String avatar;

  /** 电话号码（手机号码） */
  @Pattern(regexp = "^1\\d{10}$")
  @Length(min = 11, max = 11)
  @Column(name = "phone_number", length = 11)
  private String phoneNumber;

  /** 邮箱地址 */
  @Email
  @Column(name = "email_address", length = 100)
  private String emailAddress;

  @Column(name = "account_non_expired", columnDefinition = "TINYINT(1)")
  private boolean accountNonExpired;

  @Column(name = "account_non_locked", columnDefinition = "TINYINT(1)")
  private boolean accountNonLocked;

  @Column(name = "credentials_non_expired", columnDefinition = "TINYINT(1)")
  private boolean credentialsNonExpired;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserRole> userRoles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (CollectionUtils.isEmpty(userRoles)) {
      return Lists.newArrayList();
    }
    return userRoles.stream().map(UserRole::getRole).toList();
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
