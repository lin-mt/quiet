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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.common.service.dto.ParentDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDictionaryDto extends ParentDto<QuietDictionaryDto> {
    
    /**
     * 数据字典类型
     */
    @Length(max = 30)
    private String type;
    
    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Length(max = 30)
    private String key;
    
    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotBlank
    @Length(max = 30)
    private String label;
    
    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;
    
}
