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

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocApiHeader;

import java.util.List;

/**
 * Header Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiHeaderService {
    
    /**
     * 根据apiId查询请求头配置信息
     *
     * @param apiId 接口文档ID
     * @return 请求头配置信息
     */
    List<DocApiHeader> listByApiId(Long apiId);
}