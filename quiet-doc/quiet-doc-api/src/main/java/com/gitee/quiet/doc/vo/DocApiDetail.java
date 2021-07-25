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

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiBody;
import com.gitee.quiet.doc.entity.DocApiFormData;
import com.gitee.quiet.doc.entity.DocApiHeader;
import com.gitee.quiet.doc.entity.DocApiPathParam;
import com.gitee.quiet.doc.entity.DocApiQuery;
import com.gitee.quiet.doc.entity.DocApiResponse;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 接口文档详细信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Builder
public class DocApiDetail {
    
    /**
     * 接口信息
     */
    private DocApi api;
    
    /**
     * 访问者信息
     */
    private List<QuietUser> visitors;
    
    /**
     * 路径参数
     */
    private List<DocApiPathParam> apiPathParam;
    
    /**
     * 请求体
     */
    private DocApiBody apiBody;
    
    /**
     * 请求参数
     */
    private List<DocApiFormData> apiFormData;
    
    /**
     * 请求头
     */
    private List<DocApiHeader> apiHeader;
    
    /**
     * 查询参数
     */
    private List<DocApiQuery> apiQuery;
    
    /**
     * 返回数据
     */
    private DocApiResponse apiResponse;
}
