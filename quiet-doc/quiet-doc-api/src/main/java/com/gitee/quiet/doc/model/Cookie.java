package com.gitee.quiet.doc.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求 cookie
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
@EqualsAndHashCode
public class Cookie {
    
    /**
     * cookie name
     */
    private String name;
    
    /**
     * cookie value
     */
    private String value;
}
