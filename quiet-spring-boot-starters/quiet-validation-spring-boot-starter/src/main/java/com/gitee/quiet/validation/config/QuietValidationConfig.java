/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.validation.config;

import com.gitee.quiet.validation.extension.ValidationResource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * validation 配置类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration(proxyBeanMethods = false)
public class QuietValidationConfig {

  public static final String QUIET_VALIDATION_MESSAGE_SOURCE = "quietValidationMessageSource";

  private static MessageSource buildMessageSource(MessageSourceProperties properties) {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    if (StringUtils.hasText(properties.getBasename())) {
      messageSource.setBasenames(
          StringUtils.commaDelimitedListToStringArray(
              StringUtils.trimAllWhitespace(properties.getBasename())));
    }
    if (properties.getEncoding() != null) {
      messageSource.setDefaultEncoding(properties.getEncoding().name());
    }
    messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
    Duration cacheDuration = properties.getCacheDuration();
    if (cacheDuration != null) {
      messageSource.setCacheMillis(cacheDuration.toMillis());
    }
    messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
    messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
    return messageSource;
  }

  @Bean(name = QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE)
  public MessageSource messageSource(
      MessageSourceProperties properties, Optional<List<ValidationResource>> resourceOptional) {
    Set<String> resources = new HashSet<>();
    resources.add("quiet-validation");
    resources.add("validation");
    resources.add("ValidationMessages");
    resourceOptional.ifPresent(
        validationResources -> {
          Set<String> allNames =
              validationResources.stream()
                  .map(ValidationResource::resourceNames)
                  .collect(Collectors.toSet())
                  .stream()
                  .flatMap(Collection::stream)
                  .collect(Collectors.toSet());
          if (CollectionUtils.isNotEmpty(allNames)) {
            resources.addAll(allNames);
          }
        });
    properties.setBasename(String.join(",", resources));
    return buildMessageSource(properties);
  }

  @Bean
  public LocalValidatorFactoryBean validatorFactoryBean(
      @Qualifier(QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE)
          MessageSource messageSource) {
    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    factoryBean.setValidationMessageSource(messageSource);
    return factoryBean;
  }
}
