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
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 请求头信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_api_header")
public class DocApiHeader extends BaseEntity {
    
    /**
     * 请求头 key
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "header_key", nullable = false, length = 30)
    private String key;
    
    /**
     * 请求头 value
     */
    @NotNull
    @Length(max = 30)
    @Column(name = "header_value", nullable = false, length = 30)
    private String value;
    
    /**
     * 文档ID
     */
    @NotNull
    @Column(name = "api_info_id", nullable = false)
    private Long apiInfoId;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Long getApiInfoId() {
        return apiInfoId;
    }
    
    public void setApiInfoId(Long apiInfoId) {
        this.apiInfoId = apiInfoId;
    }
}
