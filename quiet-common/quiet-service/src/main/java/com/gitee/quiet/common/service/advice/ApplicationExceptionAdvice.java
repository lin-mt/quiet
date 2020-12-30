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

package com.gitee.quiet.common.service.advice;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 异常统一处理.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestControllerAdvice
public class ApplicationExceptionAdvice {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionAdvice.class);
    
    /**
     * 处理业务异常.
     *
     * @param e ServiceException
     * @return Result
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<Object> handleServiceException(final ServiceException e) {
        ApplicationExceptionAdvice.LOGGER.error("业务异常", e);
        if (Objects.nonNull(e.getCode())) {
            return Result.exceptionMsg(e.getCode(), e.getMsgParam());
        }
        return Result.exception().setMessage(e.getMessage());
    }
}
