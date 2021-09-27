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
import com.gitee.quiet.common.core.entity.Serial;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 需求集合序列化.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
//@JsonComponent
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
            Set<Integer> demandIndexes = new TreeSet<>();
            List<ScrumDemand> demands = new ArrayList<>();
            Set<Long> priorityIds = new HashSet<>(value.size());
            for (int i = 0; i < value.size(); i++) {
                Object t = value.get(i);
                if (t instanceof ScrumDemand) {
                    ScrumDemand demand = (ScrumDemand) t;
                    demandIndexes.add(i);
                    demands.add(demand);
                    priorityIds.add(demand.getPriorityId());
                }
            }
            if (CollectionUtils.isNotEmpty(demandIndexes)) {
                Iterator<Integer> iterator = demandIndexes.iterator();
                Map<Long, List<ScrumDemand>> priorityToDemands = demands.stream()
                        .collect(Collectors.groupingBy(ScrumDemand::getPriorityId));
                List<ScrumPriority> priorities = priorityService.findAllByIds(priorityIds).stream().sorted()
                        .collect(Collectors.toList());
                for (ScrumPriority priority : priorities) {
                    List<ScrumDemand> sortDemands = priorityToDemands.get(priority.getId());
                    Serial.Utils.sortSerial(sortDemands);
                    int index = 0;
                    while (iterator.hasNext() && index < sortDemands.size()) {
                        value.set(iterator.next(), sortDemands.get(index));
                        index++;
                    }
                }
            }
        }
    }
}
