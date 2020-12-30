/*
 * Copyright 2020 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.common.base.utils;

import com.gitee.quiet.common.base.constant.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * message 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class MessageSourceUtil {
    
    public static String getMessage(HttpServletRequest request, MessageSource messageSource, String code,
            Object... param) {
        String acceptLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        List<Locale> locales = Collections.emptyList();
        try {
            List<Locale.LanguageRange> languageRanges =
                    StringUtils.isNoneBlank(acceptLanguage) ? Locale.LanguageRange.parse(acceptLanguage)
                            : Collections.emptyList();
            locales = languageRanges.stream().map(range -> Locale.forLanguageTag(range.getRange()))
                    .filter(locale -> StringUtils.isNoneBlank(locale.getDisplayName())).collect(Collectors.toList());
        } catch (IllegalArgumentException ignore) {
        }
        String message = null;
        try {
            if (!locales.isEmpty()) {
                for (Locale locale : locales) {
                    message = getMessageFromMessageSource(messageSource, locale, code, param);
                    if (StringUtils.isNoneBlank(message)) {
                        break;
                    }
                }
            }
            if (StringUtils.isBlank(message)) {
                message = getMessageFromMessageSource(messageSource, Locale.getDefault(), code, param);
            }
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }
    
    private static String getMessageFromMessageSource(MessageSource messageSource, Locale locale, String code,
            Object[] param) {
        String message;
        try {
            message = messageSource.getMessage(code, param, locale);
        } catch (NoSuchMessageException e) {
            message = messageSource.getMessage(CommonCode.UNKNOWN_CODE, new Object[] {code}, locale);
        }
        return message;
    }
    
    public static MessageSource buildMessageSource(MessageSourceProperties properties, String... basenames) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(basenames);
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
}
