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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.jpa.repository.QuietRepository;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

/**
 * api信息repository.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocApiInfoRepository extends QuietRepository<DocApiInfo> {

    /**
     * 根据 apiId 判断是否存在api信息
     *
     * @param apiId apiId
     * @return true：存在 false：不存在
     */
    boolean existsByApiId(Long apiId);

    /**
     * 根据apiId查询api信息
     *
     * @param apiId apiId
     * @return api信息
     */
    DocApiInfo getByApiId(Long apiId);

    /**
     * 根据 api ID 批量查询api信息
     *
     * @param apiIds api id 集合
     * @return api 信息
     */
    List<DocApiInfo> findAllByApiIdIn(Set<Long> apiIds);
}
