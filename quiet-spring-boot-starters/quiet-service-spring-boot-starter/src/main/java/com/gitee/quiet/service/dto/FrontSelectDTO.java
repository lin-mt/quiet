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

package com.gitee.quiet.service.dto;

/**
 * 提供给前端树形结构key和title属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface FrontSelectDTO {
    
    /**
     * 前端树形组件使用的 key
     *
     * @return key
     */
    Object getKey();
    
    /**
     * 前端树形组件使用的 value
     *
     * @return value
     */
    default Object getValue() {
        return getKey();
    }
    
    /**
     * 前端树形组件使用的 title
     *
     * @return title
     */
    default String getTitle() {
        return null;
    }
}
