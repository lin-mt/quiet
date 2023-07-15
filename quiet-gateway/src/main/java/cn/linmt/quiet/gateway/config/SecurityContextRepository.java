package cn.linmt.quiet.gateway.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class SecurityContextRepository extends WebSessionServerSecurityContextRepository {

  private static final String DECODING_ERROR_MESSAGE_TEMPLATE =
      "An error occurred while attempting to decode the Jwt: %s";
  private final Pattern authorizationPattern =
      Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
  private final JwtTimestampValidator validator = new JwtTimestampValidator();
  private final MappedJwtClaimSetConverter claimSetConverter =
      MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return super.save(exchange, context);
  }

  @Override
  @SneakyThrows
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isNotBlank(authorization)) {
      Matcher matcher = authorizationPattern.matcher(authorization);
      if (matcher.matches()) {
        String token = matcher.group("token");
        JWT parsedJwt = JWTParser.parse(token);
        Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
        Map<String, Object> claims =
            this.claimSetConverter.convert(parsedJwt.getJWTClaimsSet().getClaims());
        Jwt jwt =
            Jwt.withTokenValue(token)
                .headers((h) -> h.putAll(headers))
                .claims((c) -> c.putAll(claims))
                .build();
        OAuth2TokenValidatorResult result = validator.validate(jwt);
        if (result.hasErrors()) {
          Collection<OAuth2Error> errors = result.getErrors();
          String validationErrorString = getJwtValidationExceptionMessage(errors);
          throw new JwtValidationException(validationErrorString, errors);
        }
        BearerTokenAuthenticationToken bearerToken = new BearerTokenAuthenticationToken(token);
        bearerToken.setPrincipal(jwt.getSubject());
        bearerToken.setAuthenticated(true);
        return Mono.just(bearerToken).map(SecurityContextImpl::new);
      }
    }
    return super.load(exchange);
  }

  private String getJwtValidationExceptionMessage(Collection<OAuth2Error> errors) {
    for (OAuth2Error oAuth2Error : errors) {
      if (StringUtils.isNotEmpty(oAuth2Error.getDescription())) {
        return String.format(DECODING_ERROR_MESSAGE_TEMPLATE, oAuth2Error.getDescription());
      }
    }
    return "Unable to validate Jwt";
  }
}
