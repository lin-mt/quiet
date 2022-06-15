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

package com.gitee.quiet.service.utils;

import com.gitee.quiet.common.constant.service.MessageSourceCode;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;

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
            message = messageSource.getMessage(MessageSourceCode.UNKNOWN_CODE, new Object[] {code}, locale);
        }
        return message;
    }

    public static MessageSource buildMessageSource(MessageSourceProperties properties) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        if (org.springframework.util.StringUtils.hasText(properties.getBasename())) {
            messageSource.setBasenames(org.springframework.util.StringUtils.commaDelimitedListToStringArray(
                org.springframework.util.StringUtils.trimAllWhitespace(properties.getBasename())));
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
}
