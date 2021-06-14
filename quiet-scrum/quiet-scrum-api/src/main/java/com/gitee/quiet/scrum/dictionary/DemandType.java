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

package com.gitee.quiet.scrum.dictionary;

import com.gitee.quiet.common.service.jpa.entity.Dictionary;

/**
 * 需求类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DemandType extends Dictionary<DemandType> {
    
    private static final String TYPE = DemandType.class.getSimpleName();
    
    private DemandType(String key) {
        super(TYPE, key);
    }
    
    /**
     * 功能需求
     */
    public static final DemandType Function = new DemandType("Function");
    
    /**
     * 优化需求
     */
    public static final DemandType Optimize = new DemandType("Optimize");
    
}
