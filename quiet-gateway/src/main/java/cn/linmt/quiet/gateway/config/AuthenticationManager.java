package cn.linmt.quiet.gateway.config;

import cn.linmt.quiet.gateway.authentication.BearerTokenAuthenticationToken;
import cn.linmt.quiet.gateway.authentication.JwtAuthenticationConverter;
import cn.linmt.quiet.gateway.authentication.ReactiveJwtAuthenticationConverterAdapter;
import cn.linmt.quiet.gateway.properties.GatewayProperties;
import cn.linmt.quiet.gateway.spring.InvalidBearerTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Rob Winch
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 * @since 5.1
 */
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager, InitializingBean {

  private final GatewayProperties properties;
  private final ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter =
      new ReactiveJwtAuthenticationConverterAdapter(new JwtAuthenticationConverter());
  private ReactiveJwtDecoder jwtDecoder;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.justOrEmpty(authentication)
        .filter((a) -> a instanceof BearerTokenAuthenticationToken)
        .cast(BearerTokenAuthenticationToken.class)
        .map(BearerTokenAuthenticationToken::getToken)
        .flatMap(this.jwtDecoder::decode)
        .flatMap(this.jwtAuthenticationConverter::convert)
        .cast(Authentication.class)
        .onErrorMap(JwtException.class, this::onError);
  }

  private AuthenticationException onError(JwtException ex) {
    if (ex instanceof BadJwtException) {
      return new InvalidBearerTokenException(ex.getMessage(), ex);
    }
    return new AuthenticationServiceException(ex.getMessage(), ex);
  }

  @Override
  public void afterPropertiesSet() {
    NimbusReactiveJwtDecoder.JwkSetUriReactiveJwtDecoderBuilder builder =
        NimbusReactiveJwtDecoder.withIssuerLocation(this.properties.getIssuerUri());
    NimbusReactiveJwtDecoder jwtDecoder = builder.build();
    jwtDecoder.setJwtValidator(
        JwtValidators.createDefaultWithIssuer(this.properties.getIssuerUri()));
    this.jwtDecoder = jwtDecoder;
  }
}
