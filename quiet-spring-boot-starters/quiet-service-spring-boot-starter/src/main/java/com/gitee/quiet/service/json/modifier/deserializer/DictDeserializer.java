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

package com.gitee.quiet.service.json.modifier.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.gitee.quiet.jpa.entity.Dict;

import java.io.IOException;

/**
 * 数据字典反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DictDeserializer extends BeanDeserializer {

  private final BeanDeserializer defaultBeanDeserializer;

  public DictDeserializer(BeanDeserializer defaultBeanDeserializer) {
    super(defaultBeanDeserializer);
    this.defaultBeanDeserializer = defaultBeanDeserializer;
  }

  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException {
    if (JsonToken.VALUE_STRING.equals(jsonParser.getCurrentToken())) {
      Dict dict = new Dict();
      dict.setKey(jsonParser.getText());
      return dict;
    }
    return defaultBeanDeserializer.deserialize(jsonParser, context);
  }
}
