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
import com.gitee.quiet.doc.enums.ContentType;
import com.gitee.quiet.doc.enums.HttpBodyType;
import com.gitee.quiet.doc.enums.HttpMethod;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文档信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "doc_api_info")
public class DocApiInfo extends SerialEntity {
    
    /**
     * 接口名称
     */
    @NotBlank
    @Length(max = 30)
    @Column(name = "api_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 请求地址
     */
    @NotBlank
    @Length(max = 300)
    @Column(name = "url", nullable = false, length = 300)
    private String url;
    
    /**
     * 请求方法
     */
    @NotNull
    @Length(max = 7)
    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 7)
    private HttpMethod method;
    
    /**
     * ContentType
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", length = 27)
    private ContentType contentType;
    
    /**
     * body 类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false, length = 6)
    private HttpBodyType bodyType = HttpBodyType.NONE;
    
    /**
     * 备注
     */
    @Length(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public HttpMethod getMethod() {
        return method;
    }
    
    public void setMethod(HttpMethod method) {
        this.method = method;
    }
    
    public ContentType getContentType() {
        return contentType;
    }
    
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    
    public HttpBodyType getBodyType() {
        return bodyType;
    }
    
    public void setBodyType(HttpBodyType bodyType) {
        this.bodyType = bodyType;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
