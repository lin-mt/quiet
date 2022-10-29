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

package com.gitee.quiet.web.json.modifier.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.gitee.quiet.common.core.entity.Sortable;

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
  public Collection<Object> deserialize(JsonParser parser, DeserializationContext context)
      throws IOException {
    Collection<Object> result = super.deserialize(parser, context);
    int index = 0;
    for (Object o : result) {
      if (o instanceof Sortable) {
        ((Sortable) o).setSortNum(index);
        index++;
      }
    }
    return result;
  }

  @Override
  public CollectionDeserializer createContextual(
      DeserializationContext context, BeanProperty property) throws JsonMappingException {
    return new CustomCollectionDeserializer(super.createContextual(context, property));
  }
}
