/*
 * Copyright $.today.year lin-mt@outlook.com
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

package com.gitee.quiet.common.service.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;

/**
 * 实体Set属性转换数据库String.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class SetStringConverter implements AttributeConverter<Set<String>, String> {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    private static final TypeReference<Set<String>> reference = new TypeReference<>() {
    };
    
    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return null;
        }
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        Set<String> attribute = new HashSet<>();
        if (StringUtils.isBlank(dbData)) {
            return attribute;
        }
        try {
            return mapper.readValue(dbData, reference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}