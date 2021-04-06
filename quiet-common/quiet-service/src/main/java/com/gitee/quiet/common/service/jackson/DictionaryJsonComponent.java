package com.gitee.quiet.common.service.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 数据字典序列化与反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class DictionaryJsonComponent {
    
    /**
     * com.gitee.quiet.common.service.jpa.entity.Dictionary 反序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class DictionaryDeserializer extends JsonDeserializer<Dictionary> {
        
        @Override
        public Dictionary deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            if (StringUtils.isNoneBlank(jsonParser.getText())) {
                return Dictionary.convertFromString(jsonParser.getText());
            }
            return null;
        }
    }
    
    /**
     * com.gitee.quiet.common.service.jpa.entity.Dictionary 序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class DictionarySerializer extends JsonSerializer<Dictionary> {
        
        @Override
        public void serialize(Dictionary dictionaryValue, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
            if (dictionaryValue != null) {
                jsonGenerator.writeString(Dictionary.convertToString(dictionaryValue));
            }
        }
    }
    
}
