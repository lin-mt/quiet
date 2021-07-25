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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.gitee.quiet.doc.enums.FormDataType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * FormData 参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_form_data")
public class DocApiFormData extends BaseEntity {
    
    /**
     * 参数名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "param_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 参数最大长度
     */
    @Column(name = "max_length")
    private Long maxLength;
    
    /**
     * 参数最小长度
     */
    @Column(name = "min_length")
    private Long minLength;
    
    /**
     * 参数类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "param_type", nullable = false, length = 4)
    private FormDataType type;
    
    /**
     * 字段是否必须
     */
    @ColumnDefault("0")
    @Column(name = "required", columnDefinition = "TINYINT(1)")
    private Boolean required;
    
    /**
     * 参数例子
     */
    @Length(max = 30)
    @Column(name = "example", length = 30)
    private String example;
    
    /**
     * 文档ID
     */
    @NotNull
    @Column(name = "api_id", nullable = false)
    private Long apiId;
    
    /**
     * 备注
     */
    @Length(max = 300)
    @Column(name = "remark", length = 300)
    private String remark;
    
}
