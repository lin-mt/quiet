package cn.linmt.quiet.system.config;

import java.util.Collection;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class JwtConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final String ROLE_CLAIM_NAME = "role";
  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
    Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
    if (jwt.hasClaim(ROLE_CLAIM_NAME)) {
      Object roleClaim = jwt.getClaim(ROLE_CLAIM_NAME);
      if (roleClaim instanceof Collection) {
        Collection<String> roles = castAuthoritiesToCollection(roleClaim);
        if (CollectionUtils.isNotEmpty(roles)) {
          authorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).toList());
        }
      }
    }
    return authorities;
  }

  @SuppressWarnings("unchecked")
  private Collection<String> castAuthoritiesToCollection(Object authorities) {
    return (Collection<String>) authorities;
  }
}
