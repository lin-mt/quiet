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

package com.gitee.quiet.common.service.json.modifier.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;

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

