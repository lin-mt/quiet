package com.gitee.quiet.common.service.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.json.jackson.modifier.serializer.CustomListSerializer;
import com.gitee.quiet.common.service.json.jackson.modifier.serializer.DictionarySerializer;

import java.util.List;

/**
 * QuietSerializerModifier.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietSerializerModifier extends BeanSerializerModifier {
    
    @Override
    public JsonSerializer<?> modifyCollectionSerializer(SerializationConfig config, CollectionType valueType,
            BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if (serializer instanceof IndexedListSerializer && List.class.isAssignableFrom(beanDesc.getBeanClass())) {
            return new CustomListSerializer((IndexedListSerializer) serializer, config.getTypeFactory());
        }
        return super.modifyCollectionSerializer(config, valueType, beanDesc, serializer);
    }
    
    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc,
            JsonSerializer<?> serializer) {
        if (Dictionary.class.isAssignableFrom(beanDesc.getBeanClass()) && serializer instanceof BeanSerializerBase) {
            return new DictionarySerializer((BeanSerializerBase) serializer);
        }
        return super.modifySerializer(config, beanDesc, serializer);
    }
}
