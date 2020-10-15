package com.gitee.quite.common.service.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.gitee.quite.common.service.base.DataDictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * 数据字典反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class DataDictionaryDeserializer<T extends DataDictionary<T>> extends JsonDeserializer<DataDictionary<T>> {
    
    @Override
    public DataDictionary<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        if (!StringUtils.isBlank(jsonParser.getText())) {
            DataDictionary<T> dictionary = new DataDictionary<>();
            dictionary.setCode(jsonParser.getText());
            return dictionary;
        }
        return null;
    }
}
