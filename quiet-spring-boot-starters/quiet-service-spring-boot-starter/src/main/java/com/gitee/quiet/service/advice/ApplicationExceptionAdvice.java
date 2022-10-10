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

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

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
      Object[] msgParam = exception.getMsgParam();
      if (ArrayUtils.isNotEmpty(msgParam)) {
        Object[] newParams = new Object[msgParam.length];
        for (int i = 0; i < msgParam.length; i++) {
          Object param = msgParam[i];
          if (param instanceof Number) {
            param = param.toString();
          }
          newParams[i] = param;
        }
        msgParam = newParams;
      }
      return Result.exceptionMsg(exception.getCode(), msgParam);
    }
    Result<Object> exceptionResult = Result.exception();
    exceptionResult.setMessage(exception.getMessage());
    return exceptionResult;
  }
}
