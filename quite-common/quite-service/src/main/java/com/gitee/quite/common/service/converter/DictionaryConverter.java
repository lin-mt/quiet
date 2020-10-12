package com.gitee.quite.common.service.converter;

import com.gitee.quite.common.service.base.DatabaseDictionary;
import com.gitee.quite.common.service.base.Dictionary;
import com.gitee.quite.common.service.config.QuiteServiceConfig;
import com.gitee.quite.common.service.util.MessageUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据字典转换器，主要是实现国际化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DictionaryConverter implements Converter<Dictionary, Dictionary>, ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    @Override
    public Dictionary convert(@NonNull Dictionary dictionary) {
        if (!dictionary.getClass().isEnum()) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (servletRequestAttributes != null) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                String message = MessageUtils.getMessage(request, applicationContext
                                .getBean(QuiteServiceConfig.QUITE_DICTIONARY_MESSAGE_SOURCE, MessageSource.class),
                        Dictionary.buildDatabaseMessageSourceKey(dictionary.getCode()));
                DatabaseDictionary databaseDictionary = new DatabaseDictionary();
                databaseDictionary.setCode(dictionary.getCode());
                databaseDictionary.setValue(message);
                return databaseDictionary;
            }
        }
        return dictionary;
    }
    
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
