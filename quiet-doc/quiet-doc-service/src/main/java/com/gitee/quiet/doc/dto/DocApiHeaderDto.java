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

package com.gitee.quiet.doc.dto;

import com.gitee.quiet.common.service.base.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 请求头信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiHeaderDto extends BaseDto {
    
    /**
     * 请求头名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 请求头 value
     */
    @Length(max = 30)
    private String value;
    
    /**
     * 字段是否必须
     */
    @ColumnDefault("0")
    private Boolean required;
    
    /**
     * 文档ID
     */
    @NotNull
    private Long apiId;
    
    /**
     * 备注
     */
    @Length(max = 100)
    private String remark;
    
}
