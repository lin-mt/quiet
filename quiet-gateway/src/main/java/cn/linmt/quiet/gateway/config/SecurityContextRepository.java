package cn.linmt.quiet.gateway.config;

import cn.linmt.quiet.gateway.authentication.BearerTokenAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private final AuthenticationManager authenticationManager;

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
        .filter(authHeader -> authHeader.startsWith("Bearer "))
        .flatMap(
            authHeader -> {
              String authToken = authHeader.substring(7);
              Authentication auth = new BearerTokenAuthenticationToken(authToken);
              return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
            });
  }
}
