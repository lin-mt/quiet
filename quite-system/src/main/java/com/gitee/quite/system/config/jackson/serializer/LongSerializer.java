package com.gitee.quite.system.config.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * java.lang.Long 转 java.lang.String.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class LongSerializer extends JsonSerializer<Long> {
    
    @Override
    public void serialize(Long longValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (longValue != null) {
            jsonGenerator.writeString(longValue.toString());
        }
    }
}
