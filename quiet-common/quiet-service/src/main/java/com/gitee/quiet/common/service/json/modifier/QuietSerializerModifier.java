/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.common.service.json.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.gitee.quiet.common.service.json.modifier.serializer.CustomListSerializer;
import com.gitee.quiet.common.service.json.modifier.serializer.DictionarySerializer;

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
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if (Dictionary.class.isAssignableFrom(beanDesc.getBeanClass()) && serializer instanceof BeanSerializerBase) {
            return new DictionarySerializer((BeanSerializerBase) serializer);
        }
        return super.modifySerializer(config, beanDesc, serializer);
    }
}