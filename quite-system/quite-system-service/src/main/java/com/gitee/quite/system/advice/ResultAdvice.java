package com.gitee.quite.system.advice;

import com.gitee.quite.system.config.ApplicationConfig;
import com.gitee.quite.system.constant.CommonCode;
import com.gitee.quite.system.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Result 统一处理.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestControllerAdvice
public class ResultAdvice<T> implements ResponseBodyAdvice<Result<T>> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultAdvice.class);
    
    @Resource(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    private MessageSource messageSource;
    
    @Resource(name = ApplicationConfig.COMMON_MESSAGE_SOURCE)
    private MessageSource commonMessageSource;
    
    @Override
    public boolean supports(final MethodParameter methodParameter,
            @NonNull final Class<? extends HttpMessageConverter<?>> aClass) {
        return Result.class.isAssignableFrom(methodParameter.getParameterType());
    }
    
    @Override
    public Result<T> beforeBodyWrite(final Result<T> result, @NonNull final MethodParameter methodParameter,
            @NonNull final MediaType mediaType, @NonNull final Class<? extends HttpMessageConverter<?>> aClass,
            @NonNull final ServerHttpRequest serverHttpRequest, @NonNull final ServerHttpResponse serverHttpResponse) {
        if (Objects.nonNull(result) && StringUtils.isBlank(result.getMessage())) {
            if (Objects.isNull(result.getCode()) && result.getCurdType() != null) {
                result.setCode(result.getCurdType().getCode());
            }
            if (StringUtils.isNoneBlank(result.getCode())) {
                List<Locale> locales = Collections.emptyList();
                try {
                    locales = serverHttpRequest.getHeaders().getAcceptLanguageAsLocales();
                } catch (IllegalArgumentException ignore) {
                }
                String message = null;
                try {
                    if (!locales.isEmpty()) {
                        for (Locale locale : locales) {
                            message = getMessage(locale, result.getCode(), result.getMsgParam());
                            if (StringUtils.isNoneBlank(message)) {
                                break;
                            }
                        }
                    }
                    if (StringUtils.isBlank(message)) {
                        message = getMessage(Locale.getDefault(), result.getCode(), result.getMsgParam());
                    }
                } catch (NoSuchMessageException ignored) {
                }
                if (StringUtils.isNoneBlank(message)) {
                    result.setMessage(message);
                }
            }
        }
        LOGGER.info("返回数据: {}", result);
        return result;
    }
    
    private String getMessage(Locale locale, String code, Object... param) {
        String message;
        try {
            if (code.startsWith(CommonCode.PREFIX)) {
                message = commonMessageSource.getMessage(CommonCode.removePrefix(code), param, locale);
            } else {
                message = messageSource.getMessage(code, param, locale);
            }
        } catch (NoSuchMessageException e) {
            message = commonMessageSource.getMessage(CommonCode.UNKNOWN_CODE, new Object[] {code}, locale);
        }
        return message;
    }
}