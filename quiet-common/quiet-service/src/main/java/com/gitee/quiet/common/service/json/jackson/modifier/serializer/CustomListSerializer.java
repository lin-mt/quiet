/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.common.service.json.jackson.modifier.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase;
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
 * List 序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CustomListSerializer extends AsArraySerializerBase<List<Object>> {
    
    private final IndexedListSerializer defaultSerializer;
    
    public CustomListSerializer(IndexedListSerializer defaultSerializer, TypeFactory tf) {
        super(List.class, tf.constructSimpleType(Object.class, new JavaType[] {}), false, null, null);
        this.defaultSerializer = defaultSerializer;
    }
    
    private CustomListSerializer(CustomListSerializer src, BeanProperty prop, TypeSerializer vts, JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
        super(src, prop, vts, valueSerializer, unwrapSingle);
        this.defaultSerializer = src.defaultSerializer;
    }
    
    @Override
    public AsArraySerializerBase<List<Object>> withResolved(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
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
    protected void serializeContents(List<Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
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
