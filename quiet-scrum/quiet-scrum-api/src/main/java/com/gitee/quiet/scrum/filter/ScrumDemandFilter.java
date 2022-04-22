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

package com.gitee.quiet.scrum.filter;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.scrum.dictionary.DemandType;
import lombok.Getter;
import lombok.Setter;

/**
 * 需求过滤条件.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumDemandFilter {

    /**
     * 是否已规划
     */
    private Boolean planned;

    /**
     * 优先级ID
     */
    private Long priorityId;

    /**
     * 需求类型
     */
    private Dictionary<DemandType> demandType;

}
