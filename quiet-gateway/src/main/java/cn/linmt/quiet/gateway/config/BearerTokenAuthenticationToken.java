package cn.linmt.quiet.gateway.config;

import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class BearerTokenAuthenticationToken extends AbstractAuthenticationToken {

  private final String token;

  private String principal;

  public BearerTokenAuthenticationToken(String token) {
    super(Collections.emptyList());
    Assert.hasText(token, "token cannot be empty");
    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  @Override
  public Object getCredentials() {
    return this.getToken();
  }

  @Override
  public Object getPrincipal() {
    if (StringUtils.isNotBlank(principal)) {
      return this.principal;
    }
    return this.getToken();
  }

  public void setPrincipal(String principal) {
    this.principal = principal;
  }
}
