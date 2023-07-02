package cn.linmt.quiet.gateway.model;

import lombok.Data;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
public class Token {

  private String accessToken;

  private String refreshToken;

  private String tokenType;

  private Long expiresIn;
}
