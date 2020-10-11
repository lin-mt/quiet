package com.gitee.quite.system.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * java.lang.Long 转 java.lang.String.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
public class LongConverter implements Converter<Long, String> {
    
    @Override
    public String convert(Long longValue) {
        return longValue.toString();
    }
    
}