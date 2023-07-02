/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.linmt.quiet.gateway.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogMessage;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Eric Deandrea
 * @since 5.2
 */
public final class JwtGrantedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final String DEFAULT_AUTHORITY_PREFIX = "SCOPE_";
  private static final String DEFAULT_AUTHORITIES_CLAIM_DELIMITER = " ";
  private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES =
      Arrays.asList("scope", "scp");
  private final Log logger = LogFactory.getLog(getClass());
  private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

  private String authoritiesClaimDelimiter = DEFAULT_AUTHORITIES_CLAIM_DELIMITER;

  private String authoritiesClaimName;

  /**
   * Extract {@link GrantedAuthority}s from the given {@link Jwt}.
   *
   * @param jwt The {@link Jwt} token
   * @return The {@link GrantedAuthority authorities} read from the token scopes
   */
  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (String authority : getAuthorities(jwt)) {
      grantedAuthorities.add(new SimpleGrantedAuthority(this.authorityPrefix + authority));
    }
    return grantedAuthorities;
  }

  /**
   * Sets the prefix to use for {@link GrantedAuthority authorities} mapped by this converter.
   * Defaults to {@link JwtGrantedAuthoritiesConverter#DEFAULT_AUTHORITY_PREFIX}.
   *
   * @param authorityPrefix The authority prefix
   * @since 5.2
   */
  public void setAuthorityPrefix(String authorityPrefix) {
    Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
    this.authorityPrefix = authorityPrefix;
  }

  /**
   * Sets the regex to use for splitting the value of the authorities claim into {@link
   * GrantedAuthority authorities}. Defaults to {@link
   * JwtGrantedAuthoritiesConverter#DEFAULT_AUTHORITIES_CLAIM_DELIMITER}.
   *
   * @param authoritiesClaimDelimiter The regex used to split the authorities
   * @since 6.1
   */
  public void setAuthoritiesClaimDelimiter(String authoritiesClaimDelimiter) {
    Assert.notNull(authoritiesClaimDelimiter, "authoritiesClaimDelimiter cannot be null");
    this.authoritiesClaimDelimiter = authoritiesClaimDelimiter;
  }

  /**
   * Sets the name of token claim to use for mapping {@link GrantedAuthority authorities} by this
   * converter. Defaults to {@link
   * JwtGrantedAuthoritiesConverter#WELL_KNOWN_AUTHORITIES_CLAIM_NAMES}.
   *
   * @param authoritiesClaimName The token claim name to map authorities
   * @since 5.2
   */
  public void setAuthoritiesClaimName(String authoritiesClaimName) {
    Assert.hasText(authoritiesClaimName, "authoritiesClaimName cannot be empty");
    this.authoritiesClaimName = authoritiesClaimName;
  }

  private String getAuthoritiesClaimName(Jwt jwt) {
    if (this.authoritiesClaimName != null) {
      return this.authoritiesClaimName;
    }
    for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
      if (jwt.hasClaim(claimName)) {
        return claimName;
      }
    }
    return null;
  }

  private Collection<String> getAuthorities(Jwt jwt) {
    String claimName = getAuthoritiesClaimName(jwt);
    if (claimName == null) {
      this.logger.trace(
          "Returning no authorities since could not find any claims that might contain scopes");
      return Collections.emptyList();
    }
    if (this.logger.isTraceEnabled()) {
      this.logger.trace(LogMessage.format("Looking for scopes in claim %s", claimName));
    }
    Object authorities = jwt.getClaim(claimName);
    if (authorities instanceof String) {
      if (StringUtils.hasText((String) authorities)) {
        return Arrays.asList(((String) authorities).split(this.authoritiesClaimDelimiter));
      }
      return Collections.emptyList();
    }
    if (authorities instanceof Collection) {
      return castAuthoritiesToCollection(authorities);
    }
    return Collections.emptyList();
  }

  @SuppressWarnings("unchecked")
  private Collection<String> castAuthoritiesToCollection(Object authorities) {
    return (Collection<String>) authorities;
  }
}
