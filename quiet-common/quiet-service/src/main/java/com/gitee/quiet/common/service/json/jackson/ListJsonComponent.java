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

package com.gitee.quiet.common.service.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.quiet.common.service.base.Serial;
import com.gitee.quiet.common.service.util.GsonUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * List 的序列化与反序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class ListJsonComponent {
    
    /**
     * java.util.List 序列化
     *
     * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
     */
    public static class ListSerializer<T> extends JsonSerializer<List<T>> {
        
        @Override
        public void serialize(List<T> listValue, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (listValue == null) {
                jsonGenerator.writeNull();
            } else if (listValue.size() == 0) {
                jsonGenerator.writeStartArray();
                jsonGenerator.writeEndArray();
            } else {
                Map<Integer, T> indexToValue = new HashMap<>(listValue.size());
                for (int i = 0; i < listValue.size(); i++) {
                    T t = listValue.get(i);
                    if (t instanceof Serial) {
                        indexToValue.put(i, t);
                    }
                }
                if (MapUtils.isNotEmpty(indexToValue)) {
                    List<T> sort = indexToValue.values().stream().sorted().collect(Collectors.toList());
                    int index = 0;
                    for (Map.Entry<Integer, T> entry : indexToValue.entrySet()) {
                        listValue.set(entry.getKey(), sort.get(index));
                        index++;
                    }
                }
                jsonGenerator.writeObject(GsonUtil.fromJson(GsonUtil.toJson(listValue), Object[].class));
            }
            jsonGenerator.flush();
        }
    }
}
