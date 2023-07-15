package cn.linmt.quiet.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class ServiceConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(new JwtConverter());
    http.authorizeHttpRequests(registry -> registry.anyRequest().authenticated())
        .oauth2ResourceServer(
            configurer ->
                configurer.jwt(
                    jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(converter)));
    return http.build();
  }
}
