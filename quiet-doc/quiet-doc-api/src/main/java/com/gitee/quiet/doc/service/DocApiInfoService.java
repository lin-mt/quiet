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

import com.gitee.quiet.doc.entity.DocApiInfo;

/**
 * api 信息 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
public interface DocApiInfoService {
    
    /**
     * 新增api信息
     *
     * @param save 新增的api信息
     * @return 新增的api数据库信息
     */
    DocApiInfo save(DocApiInfo save);
    
    /**
     * 更新api信息
     *
     * @param update 要更新的api信息
     * @return 更新后的api信息
     */
    DocApiInfo update(DocApiInfo update);
    
    /**
     * 根据apiId查询api信息
     *
     * @param apiId apiId
     * @return api信息
     */
    DocApiInfo getByApiId(Long apiId);
}
