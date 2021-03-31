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

package com.gitee.quiet.common.service.jpa.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Set<Long> 与 String 的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter
public class SetLongStringConverter implements AttributeConverter<Set<Long>, String> {
    
    @Override
    public String convertToDatabaseColumn(Set<Long> attribute) {
        if (CollectionUtils.isEmpty(attribute)) {
            return null;
        }
        return String.join(ConverterConstant.DELIMITER,
                attribute.stream().map(Object::toString).collect(Collectors.toSet()));
    }
    
    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        Set<Long> attribute = new HashSet<>();
        if (StringUtils.isBlank(dbData)) {
            return attribute;
        }
        StringTokenizer st = new StringTokenizer(dbData, ConverterConstant.DELIMITER);
        while (st.hasMoreTokens()) {
            attribute.add(Long.parseLong(st.nextToken()));
        }
        return attribute;
    }
}