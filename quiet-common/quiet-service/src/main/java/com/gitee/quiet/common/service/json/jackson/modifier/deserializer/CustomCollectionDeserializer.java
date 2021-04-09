package com.gitee.quiet.common.service.json.jackson.modifier.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.gitee.quiet.common.service.base.Serial;

import java.io.IOException;
import java.util.Collection;

/**
 * 集合反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CustomCollectionDeserializer extends CollectionDeserializer {
    
    public CustomCollectionDeserializer(CollectionDeserializer deserializer) {
        super(deserializer);
    }
    
    @Override
    public Collection<Object> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Collection<Object> result = super.deserialize(parser, context);
        int index = 0;
        for (Object o : result) {
            if (o instanceof Serial) {
                ((Serial) o).setSerialNumber(index);
                index++;
            }
        }
        return result;
    }
    
    @Override
    public CollectionDeserializer createContextual(DeserializationContext context, BeanProperty property)
            throws JsonMappingException {
        return new CustomCollectionDeserializer(super.createContextual(context, property));
    }
}

