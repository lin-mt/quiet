package com.gitee.quite.system.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.quite.system.exception.ServiceException;

import java.util.Arrays;

/**
 * 返回结果的实体.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class Result<T> {
    
    /**
     * 结果类型.
     */
    private final ResultType result;
    
    /**
     * msg 格式编码.
     */
    private String code;
    
    /**
     * 返回信息.
     */
    private String message;
    
    /**
     * 执行结果.
     */
    private T data;
    
    /**
     * 信息的参数.
     */
    @JsonIgnore
    private Object[] msgParam;
    
    /**
     * CURD 的结果.
     */
    @JsonIgnore
    private CurdType curdType;
    
    private Result(final ResultType resultType, final String code, final Object[] msgParam) {
        if (resultType == null) {
            throw new IllegalArgumentException("ResultType cannot be null.");
        }
        this.result = resultType;
        this.code = code;
        this.msgParam = msgParam;
    }
    
    private Result(final ResultType resultType, final T data) {
        if (resultType == null) {
            throw new IllegalArgumentException("ResultType cannot be null.");
        }
        this.result = resultType;
        this.data = data;
    }
    
    private Result(final ResultType resultType) {
        if (resultType == null) {
            throw new IllegalArgumentException("ResultType cannot be null.");
        }
        this.result = resultType;
    }
    
    /**
     * 出现异常.
     *
     * @param <T> 返回的数据类型
     * @return 返回数据
     */
    public static <T> Result<T> exception() {
        return new Result<>(ResultType.EXCEPTION);
    }
    
    /**
     * 异常，携带数据.
     *
     * @param <T>  返回的数据类型
     * @param data 数据
     * @return 返回数据
     */
    public static <T> Result<T> exception(final T data) {
        return new Result<>(ResultType.EXCEPTION, data);
    }
    
    /**
     * 执行成功.
     *
     * @param <T> 返回的数据类型
     * @return 返回数据
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultType.SUCCESS);
    }
    
    /**
     * 执行成功，带数据.
     *
     * @param <T>  返回的数据类型
     * @param data 数据
     * @return 返回数据
     */
    public static <T> Result<T> success(final T data) {
        return new Result<>(ResultType.SUCCESS, data);
    }
    
    /**
     * 执行成功，携带信息和数据.
     *
     * @param <T>      返回的数据类型
     * @param data     数据
     * @param code     成功信息编码
     * @param msgParam 返回的信息模板的参数
     * @return 返回数据
     */
    public static <T> Result<T> success(final T data, final String code, final Object... msgParam) {
        return Result.success(data).setCode(code).setMsgParam(msgParam);
    }
    
    /**
     * 执行成功，带消息.
     *
     * @param <T>      返回的数据类型
     * @param code     成功编码
     * @param msgParam 消息对应的参数
     * @return 返回数据
     */
    public static <T> Result<T> successMsg(final String code, final Object... msgParam) {
        return new Result<>(ResultType.SUCCESS, code, msgParam);
    }
    
    /**
     * 出现失败.
     *
     * @param <T> 返回的数据类型
     * @return 返回数据
     */
    public static <T> Result<T> failure() {
        return new Result<>(ResultType.FAILURE);
    }
    
    /**
     * 失败，携带数据.
     *
     * @param <T>  返回的数据类型
     * @param data 数据
     * @return 返回数据
     */
    public static <T> Result<T> failure(final T data) {
        return new Result<>(ResultType.FAILURE, data);
    }
    
    /**
     * 失败，携带信息和数据.
     *
     * @param <T>      返回的数据类型
     * @param data     数据
     * @param code     成功信息编码
     * @param msgParam 返回的信息模板的参数
     * @return 返回数据
     */
    public static <T> Result<T> failure(final T data, final String code, final Object... msgParam) {
        return Result.failure(data).setCode(code).setMsgParam(msgParam);
    }
    
    /**
     * 出错，携带失败信息.
     *
     * @param <T>      返回的数据类型
     * @param code     错误编码
     * @param msgParam 消息对应的参数
     * @return 返回数据
     */
    public static <T> Result<T> failureMsg(final String code, final Object... msgParam) {
        return new Result<>(ResultType.FAILURE, code, msgParam);
    }
    
    /**
     * 警告.
     *
     * @param <T> 返回的数据类型
     * @return 返回数据
     */
    public static <T> Result<T> warning() {
        return new Result<>(ResultType.WARNING, null, null);
    }
    
    /**
     * 无法执行，携带警告信息.
     *
     * @param <T>      返回的数据类型
     * @param code     警告信息编码
     * @param msgParam 消息对应的参数
     * @return 返回数据
     */
    public static <T> Result<T> warningMsg(final String code, final Object... msgParam) {
        return new Result<>(ResultType.WARNING, code, msgParam);
    }
    
    /**
     * 异常结果.
     *
     * @param <T>      返回的数据类型
     * @param code     异常信息的编码
     * @param msgParam 消息对应的参数
     * @return 返回数据
     */
    public static <T> Result<T> exceptionMsg(final String code, final Object... msgParam) {
        return new Result<>(ResultType.EXCEPTION, code, msgParam);
    }
    
    /**
     * 创建成功.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> createSuccess() {
        final Result<T> result = new Result<>(ResultType.SUCCESS);
        result.curdType = CurdType.CREATE_SUCCESS;
        return result;
    }
    
    /**
     * 创建失败.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> createFailure() {
        final Result<T> result = new Result<>(ResultType.FAILURE);
        result.curdType = CurdType.CREATE_FAILURE;
        return result;
    }
    
    /**
     * 更新成功.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> updateSuccess() {
        final Result<T> result = new Result<>(ResultType.SUCCESS);
        result.curdType = CurdType.UPDATE_SUCCESS;
        return result;
    }
    
    /**
     * 更新失败.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> updateFailure() {
        final Result<T> result = new Result<>(ResultType.FAILURE);
        result.curdType = CurdType.UPDATE_FAILURE;
        return result;
    }
    
    /**
     * 查询成功.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> retrieveSuccess() {
        final Result<T> result = new Result<>(ResultType.SUCCESS);
        result.curdType = CurdType.RETRIEVE_SUCCESS;
        return result;
    }
    
    /**
     * 查询失败.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> retrieveFailure() {
        final Result<T> result = new Result<>(ResultType.FAILURE);
        result.curdType = CurdType.RETRIEVE_FAILURE;
        return result;
    }
    
    /**
     * 删除成功.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> deleteSuccess() {
        final Result<T> result = new Result<>(ResultType.SUCCESS);
        result.curdType = CurdType.DELETE_SUCCESS;
        return result;
    }
    
    /**
     * 删除失败.
     *
     * @param <T> T
     * @return result
     */
    public static <T> Result<T> deleteFailure() {
        final Result<T> result = new Result<>(ResultType.FAILURE);
        result.curdType = CurdType.DELETE_FAILURE;
        return result;
    }
    
    /**
     * 如果执行成功则获取数据，否则抛出异常.
     *
     * @return 执行结果
     */
    public T ifSuccess() {
        if (ResultType.SUCCESS.equals(getResult())) {
            return getData();
        }
        throw new ServiceException(getMessage()).setCode(getCode());
    }
    
    /**
     * 如果执行 成功/异常 则获取数据，否则抛出异常.
     *
     * @return 执行结果
     */
    public T ifSuccessOrWarning() {
        if (ResultType.SUCCESS.equals(getResult()) || ResultType.WARNING.equals(getResult())) {
            return getData();
        }
        throw new ServiceException(getMessage()).setCode(getCode());
    }
    
    public ResultType getResult() {
        return this.result;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public Result<T> setCode(final String code) {
        this.code = code;
        return this;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Result<T> setMessage(final String message) {
        this.message = message;
        return this;
    }
    
    public T getData() {
        return this.data;
    }
    
    public Result<T> setData(final T data) {
        this.data = data;
        return this;
    }
    
    public Object[] getMsgParam() {
        return this.msgParam;
    }
    
    public Result<T> setMsgParam(final Object... msgParam) {
        this.msgParam = msgParam;
        return this;
    }
    
    public CurdType getCurdType() {
        return this.curdType;
    }
    
    @Override
    public String toString() {
        return "Result{" + "result=" + result + ", code='" + code + '\'' + ", message='" + message + '\'' + ", data="
                + data + ", msgParam=" + Arrays.toString(msgParam) + ", curdType=" + curdType + '}';
    }
}