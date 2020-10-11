package com.gitee.quite.common.service.exception;

/**
 * 服务异常.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ServiceException extends RuntimeException {
    
    private static final long serialVersionUID = -9053839678620632728L;
    
    private String code;
    
    private Object[] msgParam;
    
    public ServiceException(final String code, final Object... msgParam) {
        this.code = code;
        this.msgParam = msgParam;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public ServiceException setCode(final String code) {
        this.code = code;
        return this;
    }
    
    public Object[] getMsgParam() {
        return this.msgParam;
    }
    
    public ServiceException setMsgParam(final Object[] msgParam) {
        this.msgParam = msgParam;
        return this;
    }
}
