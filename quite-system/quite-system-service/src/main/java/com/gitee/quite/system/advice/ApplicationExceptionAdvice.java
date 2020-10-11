package com.gitee.quite.system.advice;

import com.gitee.quite.system.exception.ServiceException;
import com.gitee.quite.system.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
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
    
    /**
     * 处理参数校验异常.
     *
     * @param e ServiceException
     * @return Result
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        ApplicationExceptionAdvice.LOGGER.error("参数校验异常", e);
        StringBuilder errorMsg = new StringBuilder();
        if (e.getBindingResult().hasErrors()) {
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            for (ObjectError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("; ");
            }
            errorMsg.setLength(errorMsg.length() - 2);
        }
        return Result.exception().setMessage(errorMsg.toString());
    }
}
