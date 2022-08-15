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

package com.gitee.quiet.service.json.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.service.json.modifier.deserializer.DictionaryDeserializer;

/**
 * QuietDeserializerModifier.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ServiceDeserializerModifier extends BeanDeserializerModifier {

  @Override
  public JsonDeserializer<?> modifyDeserializer(
      DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
    if (Dictionary.class.isAssignableFrom(beanDesc.getBeanClass())
        && deserializer instanceof BeanDeserializer) {
      return new DictionaryDeserializer((BeanDeserializer) deserializer);
    }
    return super.modifyDeserializer(config, beanDesc, deserializer);
  }
}
