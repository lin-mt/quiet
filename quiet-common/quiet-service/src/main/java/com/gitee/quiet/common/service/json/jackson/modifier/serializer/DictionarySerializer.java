package com.gitee.quiet.common.service.json.jackson.modifier.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
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
    
    public DictionarySerializer(BeanSerializerBase defaultSerializer, Set<String> toIgnore) {
        super(defaultSerializer, toIgnore);
        this.defaultSerializer = defaultSerializer;
    }
    
    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new DictionarySerializer(defaultSerializer, objectIdWriter, _propertyFilterId);
    }
    
    @Override
    protected BeanSerializerBase withIgnorals(Set<String> toIgnore) {
        return new DictionarySerializer(defaultSerializer, toIgnore);
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
    public void serialize(Object bean, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (bean instanceof Dictionary && ((Dictionary) bean).getId() == null) {
            gen.writeString(Dictionary.convertToString(((Dictionary) bean)));
        } else {
            defaultSerializer.serialize(bean, gen, provider);
        }
    }
}

