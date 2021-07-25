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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiBodyDto extends ParentDto<DocApiBodyDto> {
    
    /**
     * 参数名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 参数最大长度
     */
    private Long maxLength;
    
    /**
     * 参数最小长度
     */
    private Long minLength;
    
    /**
     * 参数类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private FieldType type;
    
    /**
     * 字段是否必须
     */
    private Boolean required;
    
    /**
     * 参数例子
     */
    @Length(max = 30)
    private String example;
    
    /**
     * 文档ID
     */
    @NotNull
    private Long apiId;
    
    /**
     * 备注
     */
    @Length(max = 300)
    private String remark;
    
}
