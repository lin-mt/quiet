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

package com.gitee.quiet.service.json.modifier.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.gitee.quiet.jpa.entity.Dictionary;
import java.io.IOException;
import java.util.Set;

/**
 * 数据字典序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DictionarySerializer extends BeanSerializerBase {

    private final BeanSerializerBase defaultSerializer;

    public DictionarySerializer(BeanSerializerBase defaultSerializer) {
        super(defaultSerializer);
        this.defaultSerializer = defaultSerializer;
    }

    public DictionarySerializer(BeanSerializerBase defaultSerializer, ObjectIdWriter objectIdWriter,
        Object propertyFilterId) {
        super(defaultSerializer, objectIdWriter, propertyFilterId);
        this.defaultSerializer = defaultSerializer;
    }

    public DictionarySerializer(BeanSerializerBase defaultSerializer, Set<String> toIgnore, Set<String> toInclude) {
        super(defaultSerializer, toIgnore, toInclude);
        this.defaultSerializer = defaultSerializer;
    }

    public DictionarySerializer(BeanSerializerBase defaultSerializer, BeanPropertyWriter[] properties,
        BeanPropertyWriter[] filteredProperties) {
        super(defaultSerializer, properties, filteredProperties);
        this.defaultSerializer = defaultSerializer;
    }

    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new DictionarySerializer(defaultSerializer, objectIdWriter, _propertyFilterId);
    }

    @Override
    protected BeanSerializerBase withByNameInclusion(Set<String> toIgnore, Set<String> toInclude) {
        return new DictionarySerializer(this, toIgnore, toInclude);
    }

    @Override
    protected BeanSerializerBase asArraySerializer() {
        if ((_objectIdWriter == null) && (_anyGetterWriter == null) && (_propertyFilterId == null)) {
            return new BeanAsArraySerializer(this);
        }
        return this;
    }

    @Override
    public BeanSerializerBase withFilterId(Object filterId) {
        return new DictionarySerializer(defaultSerializer, _objectIdWriter, filterId);
    }

    @Override
    protected BeanSerializerBase withProperties(BeanPropertyWriter[] properties,
        BeanPropertyWriter[] filteredProperties) {
        return new DictionarySerializer(this, properties, filteredProperties);
    }

    @Override
    public void serialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (bean instanceof Dictionary && ((Dictionary<?>) bean).getId() == null) {
            gen.writeString(Dictionary.convertToString(((Dictionary<?>) bean)));
        } else {
            defaultSerializer.serialize(bean, gen, provider);
        }
    }
}

