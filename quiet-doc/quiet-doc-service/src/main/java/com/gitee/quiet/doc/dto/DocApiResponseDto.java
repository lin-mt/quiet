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

import com.gitee.quiet.common.service.dto.ParentDto;
import com.gitee.quiet.doc.enums.FieldType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接口返回信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiResponseDto extends ParentDto<DocApiResponseDto> {
    
    /**
     * 字段名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 字段是否可能为空
     */
    private Boolean notNull;
    
    /**
     * 字段类型
     */
    @NotNull
    private FieldType type;
    
    /**
     * 例子
     */
    @Length(max = 300)
    private String example;
    
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
