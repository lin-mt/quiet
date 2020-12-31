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

package com.gitee.quiet.common.validation.advice;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.base.utils.MessageSourceUtil;
import com.gitee.quiet.common.validation.config.QuietValidationConfig;
import com.gitee.quiet.common.validation.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@RestControllerAdvice
public class ValidationExceptionAdvice {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionAdvice.class);
    
    @Resource(name = QuietValidationConfig.QUIET_VALIDATION_MESSAGE_SOURCE)
    private MessageSource messageSource;
    
    /**
     * 处理参数校验异常.
     *
     * @param e ServiceException
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ValidationExceptionAdvice.LOGGER.error("参数校验异常", e);
        StringBuilder errorMsg = new StringBuilder();
        if (e.getBindingResult().hasErrors()) {
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            for (ObjectError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append(";");
            }
        }
        return Result.exception().setMessage(errorMsg.toString());
    }
    
    /**
     * 处理自定义的参数异常.
     *
     * @param e ValidationException
     * @return Result
     */
    @ExceptionHandler(value = ValidationException.class)
    public Result<Object> handleServiceException(final ValidationException e) {
        ValidationExceptionAdvice.LOGGER.error("参数验证异常", e);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            if (Objects.nonNull(e.getCode())) {
                return Result.exception()
                        .setCode(e.getCode())
                        .setMessage(MessageSourceUtil.getMessage(request, messageSource, e.getCode(), e.getMsgParam()));
            }
        }
        return Result.exception().setMessage(e.getMessage());
    }
}
