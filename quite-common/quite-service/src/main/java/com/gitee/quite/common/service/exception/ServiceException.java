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
