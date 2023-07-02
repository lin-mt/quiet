package cn.linmt.quiet.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
@ConfigurationProperties(prefix = "quiet.gateway")
public class GatewayProperties {

  private String oauth2TokenUri;

  private String issuerUri;
}
