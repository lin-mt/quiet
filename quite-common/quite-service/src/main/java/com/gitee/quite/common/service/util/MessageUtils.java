package com.gitee.quite.common.service.util;

import com.gitee.quite.common.service.constant.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * message 工具类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class MessageUtils {
    
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
}
