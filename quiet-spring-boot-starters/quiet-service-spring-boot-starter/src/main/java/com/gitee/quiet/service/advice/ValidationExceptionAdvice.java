/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.service.advice;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.service.utils.MessageSourceUtil;
import com.gitee.quiet.validation.config.QuietValidationConfig;
import com.gitee.quiet.validation.exception.ValidationException;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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
                } else {
                    log.error("错误类型异常未处理：{}", error.getClass());
                }
                errorMsg.append(error.getDefaultMessage()).append(";");
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
