package com.gitee.quiet.common.service.json.jackson.modifier.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;

import java.io.IOException;

/**
 * 数据字典反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DictionaryDeserializer extends BeanDeserializer {
    
    private final BeanDeserializer defaultBeanDeserializer;
    
    public DictionaryDeserializer(BeanDeserializer defaultBeanDeserializer) {
        super(defaultBeanDeserializer);
        this.defaultBeanDeserializer = defaultBeanDeserializer;
    }
    
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
            return Dictionary.convertFromString(jsonParser.getText());
        }
        return defaultBeanDeserializer.deserialize(jsonParser, context);
    }
}
