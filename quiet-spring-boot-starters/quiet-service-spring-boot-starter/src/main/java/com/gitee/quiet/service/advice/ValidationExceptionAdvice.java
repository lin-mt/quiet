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

package com.gitee.quiet.service.advice;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.MessageSourceUtil;
import com.gitee.quiet.validation.config.QuietValidationConfig;
import com.gitee.quiet.validation.exception.ValidationException;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 参数验证异常.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@RestControllerAdvice
public class ValidationExceptionAdvice {

    private static final Converter<String, String> CAMEL_TO_UNDERSCORE_CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(
        CaseFormat.LOWER_UNDERSCORE);

    @Resource(name = QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE)
    private MessageSource messageSource;

    /**
     * 处理参数校验异常.
     *
     * @param e ServiceException
     * @return Result
     */
    @ExceptionHandler(value = BindException.class)
    public Result<Object> handleMethodArgumentNotValidException(final BindException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.printStackTrace();
        if (e.getBindingResult().hasErrors()) {
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            for (ObjectError error : errors) {
                if (error instanceof FieldError) {
                    String fieldName = ((FieldError) error).getField();
                    errorMsg.append(CAMEL_TO_UNDERSCORE_CONVERTER.convert(fieldName));
                    errorMsg.append(" ");
                } else {
                    log.error("错误类型异常未处理：{}", error.getClass());
                }
                errorMsg.append(error.getDefaultMessage()).append(";\r\n");
            }
        }
        Result<Object> exception = Result.exception();
        exception.setMessage(errorMsg.toString());
        return exception;
    }

    /**
     * 处理自定义的参数异常.
     *
     * @param e ValidationException
     * @return Result
     */
    @ExceptionHandler(value = ValidationException.class)
    public Result<Object> handleServiceException(final ValidationException e) {
        log.error("参数验证异常", e);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Result<Object> exception = Result.exception();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            if (Objects.nonNull(e.getCode())) {
                exception.setCode(e.getCode());
                exception.setMessage(
                    MessageSourceUtil.getMessage(request, messageSource, e.getCode(), e.getMsgParam()));
                return exception;
            }
        }
        exception.setMessage(e.getMessage());
        return exception;
    }
}
