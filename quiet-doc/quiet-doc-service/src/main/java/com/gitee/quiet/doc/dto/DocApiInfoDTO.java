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

import com.gitee.quiet.doc.model.FormParam;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.PathParam;
import com.gitee.quiet.doc.model.QueryParam;
import com.gitee.quiet.service.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * api信息dto.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
public class DocApiInfoDTO extends BaseDTO {
    
    /**
     * 文档ID
     */
    @NotNull
    private Long apiId;
    
    /**
     * 路径参数
     */
    private List<PathParam> pathParam;
    
    /**
     * 请求体的 jsonSchema
     */
    private Map<String, Object> reqJsonBody;
    
    /**
     * form 参数
     */
    private List<FormParam> reqForm;
    
    /**
     * 请求文件
     */
    private String reqFile;
    
    /**
     * raw
     */
    private String reqRaw;
    
    /**
     * query 参数
     */
    private List<QueryParam> reqQuery;
    
    /**
     * 请求头
     */
    private List<Header> headers;
    
    /*
     * 响应数据的 jsonSchema
     */
    private Map<String, Object> respJsonBody;
    
    /**
     * 响应信息
     */
    private String respRaw;
}
