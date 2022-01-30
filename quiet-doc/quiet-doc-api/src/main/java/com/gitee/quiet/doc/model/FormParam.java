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

package com.gitee.quiet.doc.model;

import com.gitee.quiet.doc.enums.FormParamType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Form请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
@EqualsAndHashCode
public class FormParam {
    
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 是否必须
     */
    private boolean required;
    
    /**
     * 参数类型
     */
    private FormParamType type;
    
    /**
     * 最小长度
     */
    private Long minLength;
    
    /**
     * 最大长度
     */
    private Long maxLength;
    
    /**
     * ContentType
     */
    private String contentType;
    
    /**
     * 参数示例
     */
    private String example;
    
    /**
     * 备注
     */
    private String remark;
}
