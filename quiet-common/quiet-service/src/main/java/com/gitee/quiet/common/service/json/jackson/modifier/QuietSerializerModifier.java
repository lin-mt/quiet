package com.gitee.quiet.common.service.json.jackson.modifier;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.gitee.quiet.common.service.base.Serial;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    private static class CustomListSerializer extends AsArraySerializerBase<List<Object>> {
        
        private final IndexedListSerializer defaultSerializer;
        
        public CustomListSerializer(IndexedListSerializer defaultSerializer, TypeFactory tf) {
            super(List.class, tf.constructSimpleType(Object.class, new JavaType[] {}), false, null, null);
            this.defaultSerializer = defaultSerializer;
        }
        
        private CustomListSerializer(CustomListSerializer src, BeanProperty prop, TypeSerializer vts,
                JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
            super(src, prop, vts, valueSerializer, unwrapSingle);
            this.defaultSerializer = src.defaultSerializer;
        }
        
        @Override
        public AsArraySerializerBase<List<Object>> withResolved(BeanProperty property, TypeSerializer vts,
                JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
            return new CustomListSerializer(this, property, vts, elementSerializer, unwrapSingle);
        }
        
        @Override
        public void serialize(List<Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (CollectionUtils.isNotEmpty(value)) {
                Map<Integer, Object> indexToValue = new HashMap<>(value.size());
                for (int i = 0; i < value.size(); i++) {
                    Object t = value.get(i);
                    if (t instanceof Serial) {
                        indexToValue.put(i, t);
                    }
                }
                if (MapUtils.isNotEmpty(indexToValue)) {
                    List<Object> sort = indexToValue.values().stream().sorted().collect(Collectors.toList());
                    int index = 0;
                    for (Map.Entry<Integer, Object> entry : indexToValue.entrySet()) {
                        value.set(entry.getKey(), sort.get(index));
                        index++;
                    }
                }
            }
            defaultSerializer.serialize(value, gen, provider);
        }
        
        @Override
        protected void serializeContents(List<Object> value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
            defaultSerializer.serializeContents(value, gen, provider);
        }
        
        @Override
        public boolean hasSingleElement(List<Object> value) {
            return defaultSerializer.hasSingleElement(value);
        }
        
        @Override
        @SuppressWarnings("AlibabaAvoidStartWithDollarAndUnderLineNaming")
        protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
            return defaultSerializer._withValueTypeSerializer(vts);
        }
    }
}
