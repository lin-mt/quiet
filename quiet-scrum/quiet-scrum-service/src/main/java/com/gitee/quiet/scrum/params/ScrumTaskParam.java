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
import com.gitee.quiet.scrum.entity.ScrumTask;

import java.util.Set;

/**
 * 任务查询参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ScrumTaskParam extends Param<ScrumTask, ScrumTask> {
    
    /**
     * 查询的需求ID集合
     */
    private Set<Long> demandIds;
    
    public Set<Long> getDemandIds() {
        return demandIds;
    }
    
    public void setDemandIds(Set<Long> demandIds) {
        this.demandIds = demandIds;
    }
}
