package cn.linmt.quiet.gateway.controller;

import cn.linmt.quiet.gateway.model.Token;
import cn.linmt.quiet.gateway.properties.GatewayProperties;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController implements InitializingBean {

  private final RestTemplate restTemplate;
  private final GatewayProperties properties;

  @Value("${spring.security.oauth2.client.registration.quiet-gateway.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.quiet-gateway.client-secret}")
  private String clientSecret;

  private String basic;

  @Override
  public void afterPropertiesSet() {
    basic =
        Base64.getEncoder()
            .encodeToString(
                String.format("%s:%s", clientId, clientSecret).getBytes(StandardCharsets.UTF_8));
  }

  @GetMapping(value = "/access_token")
  public ResponseEntity<Token> accessToken(@RequestParam String code, ServerWebExchange exchange) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    URI uri = exchange.getRequest().getURI();
    String uriStr = uri.toString();
    String query = uri.getQuery();
    String redirectUri = uriStr.replace(query, "").replace("?", "");
    headers.set("Authorization", "Basic " + basic);
    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
    requestBody.add("grant_type", "authorization_code");
    requestBody.add("redirect_uri", redirectUri);
    requestBody.add("code", code);
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(requestBody, headers);
    return restTemplate.exchange(
        properties.getOauth2TokenUri(), HttpMethod.POST, requestEntity, Token.class);
  }
}
