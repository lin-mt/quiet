/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
