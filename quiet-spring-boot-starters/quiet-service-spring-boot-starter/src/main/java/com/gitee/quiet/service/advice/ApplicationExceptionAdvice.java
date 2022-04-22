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

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.result.Result;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@RestControllerAdvice
public class ApplicationExceptionAdvice {

    /**
     * 处理业务异常.
     *
     * @param exception ServiceException
     * @return Result
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<Object> handleServiceException(final ServiceException exception) {
        log.error("业务异常", exception);
        if (Objects.nonNull(exception.getCode())) {
            return Result.exceptionMsg(exception.getCode(), exception.getMsgParam());
        }
        Result<Object> exceptionResult = Result.exception();
        exceptionResult.setMessage(exception.getMessage());
        return exceptionResult;
    }
}
