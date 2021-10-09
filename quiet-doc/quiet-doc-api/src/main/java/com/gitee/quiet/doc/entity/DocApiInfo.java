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

import com.gitee.quiet.doc.model.FormParam;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.PathParam;
import com.gitee.quiet.doc.model.QueryParam;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * api 信息，包含请求参数、请求头等信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_info")
public class DocApiInfo extends BaseEntity {
    
    /**
     * 文档ID
     */
    @NotNull
    @Column(name = "api_id")
    private Long apiId;
    
    /**
     * 路径参数
     */
    @Type(type = "json")
    @Column(name = "path_param", columnDefinition = "json")
    private List<PathParam> pathParam;
    
    /**
     * 请求体的 jsonSchema
     */
    @Type(type = "json")
    @Column(name = "req_json_body", columnDefinition = "json")
    private Map<String, Object> reqJsonBody;
    
    /**
     * form 参数
     */
    @Type(type = "json")
    @Column(name = "req_form", columnDefinition = "json")
    private List<FormParam> reqForm;
    
    /**
     * 请求文件
     */
    @Column(name = "req_file")
    private String reqFile;
    
    /**
     * raw
     */
    @Column(name = "req_raw")
    private String reqRaw;
    
    /**
     * query 参数
     */
    @Type(type = "json")
    @Column(name = "req_query", columnDefinition = "json")
    private List<QueryParam> reqQuery;
    
    /**
     * 请求头
     */
    @Type(type = "json")
    @Column(name = "headers", columnDefinition = "json")
    private List<Header> headers;
    
    /*
     * 响应数据的 jsonSchema
     */
    @Type(type = "json")
    @Column(name = "resp_json_body", columnDefinition = "json")
    private Map<String, Object> respJsonBody;
    
    /**
     * 响应信息
     */
    @Column(name = "resp_raw")
    private String respRaw;
}
