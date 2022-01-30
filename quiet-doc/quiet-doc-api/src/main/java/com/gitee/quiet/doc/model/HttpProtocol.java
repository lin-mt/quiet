package com.gitee.quiet.doc.model;

import com.gitee.quiet.jpa.enums.base.StringEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http 协议
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum HttpProtocol implements StringEnum {
    
    /**
     * http 协议
     */
    @SuppressWarnings("HttpUrlsUsage") HTTP("http://"),
    
    /**
     * https 协议
     */
    HTTPS("https://");
    
    private final String value;
    
}
