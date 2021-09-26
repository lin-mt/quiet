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

package com.gitee.quiet.web.json.modifier.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.gitee.quiet.common.core.entity.Serial;

import java.io.IOException;
import java.util.Collection;

/**
 * 集合反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class CustomCollectionDeserializer extends CollectionDeserializer {
    
    public CustomCollectionDeserializer(CollectionDeserializer deserializer) {
        super(deserializer);
    }
    
    @Override
    public Collection<Object> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Collection<Object> result = super.deserialize(parser, context);
        int index = 0;
        for (Object o : result) {
            if (o instanceof Serial) {
                ((Serial) o).setSerialNumber(index);
                index++;
            }
        }
        return result;
    }
    
    @Override
    public CollectionDeserializer createContextual(DeserializationContext context, BeanProperty property)
            throws JsonMappingException {
        return new CustomCollectionDeserializer(super.createContextual(context, property));
    }
}

