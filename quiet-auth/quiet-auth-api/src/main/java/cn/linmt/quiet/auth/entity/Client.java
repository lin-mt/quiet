package cn.linmt.quiet.auth.entity;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "auth_client")
public class Client extends QuietEntity {

  private String clientId;
  private Instant clientIdIssuedAt;
  private String clientSecret;
  private Instant clientSecretExpiresAt;
  private String clientName;

  @Column(length = 1000)
  private String clientAuthenticationMethods;

  @Column(length = 1000)
  private String authorizationGrantTypes;

  @Column(length = 1000)
  private String redirectUris;

  @Column(length = 1000)
  private String postLogoutRedirectUris;

  @Column(length = 1000)
  private String scopes;

  @Column(length = 2000)
  private String clientSettings;

  @Column(length = 2000)
  private String tokenSettings;
}
