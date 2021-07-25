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

import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 路径参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_path_param")
public class DocApiPathParam extends SerialEntity {
    
    /**
     * 参数名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "param_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 示例值
     */
    @Length(max = 30)
    @Column(name = "example", length = 30)
    private String example;
    
    /**
     * 接口ID
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
