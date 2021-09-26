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

package com.gitee.quiet.web.json.modifier.serializer;

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
import com.gitee.quiet.common.core.entity.Serial;

import java.io.IOException;
import java.util.List;

/**
 * List 序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CustomCollectionSerializer extends AsArraySerializerBase<List<Object>> {
    
    private final IndexedListSerializer defaultSerializer;
    
    public CustomCollectionSerializer(IndexedListSerializer defaultSerializer, TypeFactory tf) {
        super(List.class, tf.constructSimpleType(Object.class, new JavaType[] {}), false, null, null);
        this.defaultSerializer = defaultSerializer;
    }
    
    private CustomCollectionSerializer(CustomCollectionSerializer src, BeanProperty prop, TypeSerializer vts,
            JsonSerializer<?> valueSerializer, Boolean unwrapSingle) {
        super(src, prop, vts, valueSerializer, unwrapSingle);
        this.defaultSerializer = src.defaultSerializer;
    }
    
    public IndexedListSerializer getDefaultSerializer() {
        return defaultSerializer;
    }
    
    @Override
    public AsArraySerializerBase<List<Object>> withResolved(BeanProperty property, TypeSerializer vts,
            JsonSerializer<?> elementSerializer, Boolean unwrapSingle) {
        return new CustomCollectionSerializer(this, property, vts, elementSerializer, unwrapSingle);
    }
    
    @Override
    public void serialize(List<Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Serial.Utils.sortSerial(value);
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
