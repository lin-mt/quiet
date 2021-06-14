/*
 * Copyright (c) 2021 lin-mt@outlook.com
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

import com.gitee.quiet.common.service.jpa.entity.ParentEntity;
import com.gitee.quiet.doc.enums.HttpParamType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_api_param")
public class DocApiParam extends ParentEntity<DocApiParam> {
    
    @NotBlank
    @Length(max = 30)
    @Column(name = "param_key", nullable = false, length = 30)
    private String key;
    
    @Column(name = "param_length")
    private Long length;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "param_type", nullable = false, length = 7)
    private HttpParamType type;
    
    @NotNull
    @Length(max = 300)
    @Column(name = "example", nullable = false, length = 300)
    private String example;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "element_type", length = 7)
    private HttpParamType elementType;
    
    @NotNull
    @Column(name = "api_info_id", nullable = false)
    private Long apiInfoId;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public Long getLength() {
        return length;
    }
    
    public void setLength(Long length) {
        this.length = length;
    }
    
    public HttpParamType getType() {
        return type;
    }
    
    public void setType(HttpParamType type) {
        this.type = type;
    }
    
    public String getExample() {
        return example;
    }
    
    public void setExample(String example) {
        this.example = example;
    }
    
    public HttpParamType getElementType() {
        return elementType;
    }
    
    public void setElementType(HttpParamType elementType) {
        this.elementType = elementType;
    }
    
    public Long getApiInfoId() {
        return apiInfoId;
    }
    
    public void setApiInfoId(Long apiInfoId) {
        this.apiInfoId = apiInfoId;
    }
}
