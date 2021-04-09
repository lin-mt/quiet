package com.gitee.quiet.common.service.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.json.jackson.modifier.deserializer.CustomCollectionDeserializer;
import com.gitee.quiet.common.service.json.jackson.modifier.deserializer.DictionaryDeserializer;

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
            return new CustomCollectionDeserializer((CollectionDeserializer) deserializer);
        }
        return super.modifyCollectionDeserializer(config, type, beanDesc, deserializer);
    }
    
    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc,
            JsonDeserializer<?> deserializer) {
        if (Dictionary.class.isAssignableFrom(beanDesc.getBeanClass()) && deserializer instanceof BeanDeserializer) {
            return new DictionaryDeserializer((BeanDeserializer) deserializer);
        }
        return super.modifyDeserializer(config, beanDesc, deserializer);
    }
}
