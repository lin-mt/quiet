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

package com.gitee.quiet.web.json.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Long 的序列化方式.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class LongJsonComponent {

  /**
   * java.lang.Long 反序列化
   *
   * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
   */
  public static class LongDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      if (StringUtils.isNoneBlank(jsonParser.getText())) {
        return Long.parseLong(jsonParser.getText());
      }
      return null;
    }
  }

  /**
   * java.lang.Long 序列化
   *
   * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
   */
  public static class LongSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(
        Long longValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      if (longValue != null) {
        jsonGenerator.writeString(longValue.toString());
      }
    }
  }
}
