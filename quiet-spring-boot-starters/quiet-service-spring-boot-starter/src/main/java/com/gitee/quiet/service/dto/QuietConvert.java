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
 * 实体转换接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietConvert<E, D> {
    
    /**
     * DTO 转实体
     *
     * @param dto 转换的DTO
     * @return 实体信息
     */
    E dtoToEntity(D dto);
    
    /**
     * 实体转DTO
     *
     * @param entity 实体信息
     * @return DTO信息
     */
    D entityToDto(E entity);
}
