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

import java.util.Collection;
import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.util.Assert;

/**
 * @author Joe Grandja
 * @since 5.1
 */
public abstract class AbstractOAuth2TokenAuthenticationToken<T extends OAuth2Token>
    extends AbstractAuthenticationToken {

  private final Object principal;

  private final Object credentials;

  private final T token;

  /** Sub-class constructor. */
  protected AbstractOAuth2TokenAuthenticationToken(T token) {

    this(token, null);
  }

  /**
   * Sub-class constructor.
   *
   * @param authorities the authorities assigned to the Access Token
   */
  protected AbstractOAuth2TokenAuthenticationToken(
      T token, Collection<? extends GrantedAuthority> authorities) {

    this(token, token, token, authorities);
  }

  protected AbstractOAuth2TokenAuthenticationToken(
      T token,
      Object principal,
      Object credentials,
      Collection<? extends GrantedAuthority> authorities) {

    super(authorities);
    Assert.notNull(token, "token cannot be null");
    Assert.notNull(principal, "principal cannot be null");
    this.principal = principal;
    this.credentials = credentials;
    this.token = token;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  /** Get the token bound to this {@link Authentication}. */
  public final T getToken() {
    return this.token;
  }

  /**
   * Returns the attributes of the access token.
   *
   * @return a {@code Map} of the attributes in the access token.
   */
  public abstract Map<String, Object> getTokenAttributes();
}
