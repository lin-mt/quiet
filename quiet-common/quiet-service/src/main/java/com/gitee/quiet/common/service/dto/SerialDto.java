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

package com.gitee.quiet.common.service.dto;

import com.gitee.quiet.common.service.base.BaseDto;
import com.gitee.quiet.common.service.base.Serial;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * 具有排序字段的实体.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class SerialDto extends BaseDto implements Serial {
    
    /**
     * 序号
     */
    @Min(0)
    private int serialNumber;
    
}