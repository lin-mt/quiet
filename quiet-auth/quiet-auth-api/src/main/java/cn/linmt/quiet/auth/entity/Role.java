package cn.linmt.quiet.auth.entity;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "auth_role")
public class Role extends QuietEntity implements GrantedAuthority {

  /** 角色名称 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "authority", nullable = false, length = 30)
  private String authority;

  /** 角色中文名 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "role_name", nullable = false, length = 30)
  private String roleName;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserRole> userRoles;
}
