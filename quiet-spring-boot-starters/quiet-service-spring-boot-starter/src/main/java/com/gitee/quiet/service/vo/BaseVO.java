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

package com.gitee.quiet.service.vo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.gitee.quiet.service.json.filter.JsonFilterName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * VO 基本信息
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@JsonFilter(JsonFilterName.HAS_ROLE)
public class BaseVO {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 创建者ID
     */
    private Long creator;
    
    /**
     * 更新者ID
     */
    private Long updater;
    
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    
    /**
     * 更新时间
     */
    private LocalDateTime gmtUpdate;
}
