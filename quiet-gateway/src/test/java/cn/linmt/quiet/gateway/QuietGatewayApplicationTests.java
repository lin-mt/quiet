package cn.linmt.quiet.gateway;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@SpringBootTest
class QuietGatewayApplicationTests {

  private final Logger logger = LoggerFactory.getLogger(QuietGatewayApplicationTests.class);

  @Test
  void contextLoads() {}

  @Test
  void getAuthorizationUrl() {
    String authorizationUri = "http://127.0.0.1:8000/oauth2/authorize";
    String clientId = "quiet-gateway";
    String redirectUri = "http://127.0.0.1:8080/access_token";
    Set<String> scopes = Set.of("openid", "user.read");

    OAuth2AuthorizationRequest authorizationRequest =
        OAuth2AuthorizationRequest.authorizationCode()
            .clientId(clientId)
            .redirectUri(redirectUri)
            .authorizationUri(authorizationUri)
            .scopes(scopes)
            .build();
    String authorizationUrl = authorizationRequest.getAuthorizationRequestUri();
    logger.info(authorizationUrl);
  }
}
