package com.gitee.quiet.common.service.json.jackson.modifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gitee.quiet.common.service.base.Serial;

import java.io.IOException;
import java.util.Collection;

/**
 * QuietDeserializerModifier.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietDeserializerModifier extends BeanDeserializerModifier {
    
    @Override
    public JsonDeserializer<?> modifyCollectionDeserializer(DeserializationConfig config, CollectionType type,
            BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        if (deserializer instanceof CollectionDeserializer) {
            return new CustomerCollectionDeserializer((CollectionDeserializer) deserializer);
        }
        return super.modifyCollectionDeserializer(config, type, beanDesc, deserializer);
    }
    
    private static class CustomerCollectionDeserializer extends CollectionDeserializer {
        
        public CustomerCollectionDeserializer(CollectionDeserializer deserializer) {
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
            return new CustomerCollectionDeserializer(super.createContextual(context, property));
        }
    }
    
}
