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

package com.gitee.quiet.scrum.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gitee.quiet.common.service.base.Serial;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 需求集合序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@JsonComponent
public class ListSerializer extends JsonSerializer<List<Object>> {
    
    private final ScrumPriorityService priorityService;
    
    public ListSerializer(ScrumPriorityService priorityService) {
        this.priorityService = priorityService;
    }
    
    @Override
    public void serialize(List<Object> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            Serial.Utils.sortSerial(value);
            sortScrumDemand(value);
            gen.writeStartArray();
            for (Object o : value) {
                gen.writeObject(o);
            }
            gen.writeEndArray();
        } else {
            gen.writeNull();
        }
    }
    
    private void sortScrumDemand(List<Object> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            Map<Integer, ScrumDemand> indexToValue = new HashMap<>(value.size());
            Set<Long> priorityIds = new HashSet<>(value.size());
            for (int i = 0; i < value.size(); i++) {
                Object t = value.get(i);
                if (t instanceof ScrumDemand) {
                    ScrumDemand scrumDemand = (ScrumDemand) t;
                    indexToValue.put(i, scrumDemand);
                    priorityIds.add(scrumDemand.getPriorityId());
                }
            }
            if (MapUtils.isNotEmpty(indexToValue)) {
                Map<Long, List<ScrumDemand>> priorityToDemands = indexToValue.values().stream()
                        .collect(Collectors.groupingBy(ScrumDemand::getPriorityId));
                List<ScrumPriority> priorities = priorityService.findAllByIds(priorityIds).stream().sorted()
                        .collect(Collectors.toList());
                for (ScrumPriority priority : priorities) {
                    List<ScrumDemand> scrumDemands = priorityToDemands.get(priority.getId());
                    List<Object> objects = new ArrayList<>(scrumDemands);
                    Serial.Utils.sortSerial(objects);
                    int index = 0;
                    for (Map.Entry<Integer, ScrumDemand> entry : indexToValue.entrySet()) {
                        value.set(entry.getKey(), objects.get(index));
                        index++;
                    }
                }
            }
        }
    }
}
