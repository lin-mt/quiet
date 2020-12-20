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

package com.gitee.quiet.common.service.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.quiet.common.service.base.DataDictionary;
import com.gitee.quiet.common.service.base.Dictionary;
import com.gitee.quiet.common.service.base.EnumDictionary;
import com.gitee.quiet.common.service.config.QuietServiceConfig;
import com.gitee.quiet.common.service.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 数据字典序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class DictionarySerializer<T extends Dictionary<T>> extends JsonSerializer<Dictionary<T>>
        implements ApplicationContextAware {
    
    private ApplicationContext applicationContext;
    
    @Override
    public void serialize(Dictionary<T> dictionary, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            MessageSource messageSource = applicationContext
                    .getBean(QuietServiceConfig.QUIET_DICTIONARY_MESSAGE_SOURCE, MessageSource.class);
            String message;
            String codeFieldValue;
            jsonGenerator.writeStartObject();
            if (dictionary.getClass().isEnum()) {
                message = MessageUtils.getMessage(request, messageSource,
                        EnumDictionary.buildEnumMessageSourceKey(dictionary.getCode()));
                codeFieldValue = ((Enum<?>) dictionary).name();
            } else {
                message = MessageUtils.getMessage(request, messageSource,
                        DataDictionary.buildDatabaseMessageSourceKey(dictionary.getCode()));
                codeFieldValue = dictionary.getCode();
            }
            jsonGenerator.writeStringField(Dictionary.Field.CODE.getFieldName(), codeFieldValue);
            jsonGenerator.writeStringField(Dictionary.Field.VALUE.getFieldName(),
                    StringUtils.isBlank(message) ? dictionary.getCode() : message);
            jsonGenerator.writeEndObject();
        }
    }
    
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
