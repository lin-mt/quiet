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

package com.gitee.quiet.scrum.params;

import com.gitee.quiet.common.service.base.Param;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.filter.ScrumDemandFilter;

/**
 * 需求查询参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ScrumDemandParam extends Param<ScrumDemand, ScrumDemand> {
    
    /**
     * 迭代ID
     */
    private Long iterationId;
    
    /**
     * 过滤条件
     */
    private ScrumDemandFilter demandFilter;
    
    public ScrumDemandFilter getDemandFilter() {
        return demandFilter;
    }
    
    public void setDemandFilter(ScrumDemandFilter demandFilter) {
        this.demandFilter = demandFilter;
    }
    
    public Long getIterationId() {
        return iterationId;
    }
    
    public void setIterationId(Long iterationId) {
        this.iterationId = iterationId;
    }
    
    
}
