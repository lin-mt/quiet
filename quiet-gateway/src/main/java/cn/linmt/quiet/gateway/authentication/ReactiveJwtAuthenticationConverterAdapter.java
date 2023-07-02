/*
 * Copyright 2002-2018 the original author or authors.
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

import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * @author Josh Cummings
 * @since 5.1.1
 */
public class ReactiveJwtAuthenticationConverterAdapter
    implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

  private final Converter<Jwt, AbstractAuthenticationToken> delegate;

  public ReactiveJwtAuthenticationConverterAdapter(
      Converter<Jwt, AbstractAuthenticationToken> delegate) {
    Assert.notNull(delegate, "delegate cannot be null");
    this.delegate = delegate;
  }

  @Override
  @NotNull
  public final Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
    return Mono.just(jwt).mapNotNull(this.delegate::convert);
  }
}
