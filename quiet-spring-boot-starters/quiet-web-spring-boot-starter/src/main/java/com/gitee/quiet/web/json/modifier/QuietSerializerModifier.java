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

package com.gitee.quiet.web.json.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.gitee.quiet.web.json.modifier.serializer.CustomCollectionSerializer;
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
            return new CustomCollectionSerializer((IndexedListSerializer) serializer, config.getTypeFactory());
        }
        return super.modifyCollectionSerializer(config, valueType, beanDesc, serializer);
    }

}
