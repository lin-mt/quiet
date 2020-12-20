/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quiet.common.service.base;

/**
 * 数据字典枚举基类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Dictionary<T extends Dictionary<T>> {
    
    /**
     * code.
     *
     * @return 编码值
     */
    String getCode();
    
    /**
     * 值.
     *
     * @return 前端显示的值
     */
    default String getValue() {
        return getCode();
    }
    
    default boolean equal(Dictionary<T> other) {
        if (other == null) {
            return false;
        }
        return getCode().equals(other.getCode());
    }
    
    /**
     * 数据字典包含的字段枚举
     */
    enum Field {
        /**
         * 编码
         */
        CODE("code"),
        /**
         * 值
         */
        VALUE("value"),
        ;
        
        private final String fieldName;
        
        Field(String fieldName) {
            this.fieldName = fieldName;
        }
        
        public String getFieldName() {
            return fieldName;
        }
    }
    
}
