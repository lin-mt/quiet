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

package com.gitee.quiet.system.dictionary;

import com.gitee.quiet.common.service.jpa.entity.Dictionary;

/**
 * 环境数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class Environment extends Dictionary<Environment> {
    
    private static final String TYPE = Environment.class.getSimpleName();
    
    private Environment(String key) {
        super(TYPE, key);
    }
    
    /**
     * 开发环境
     */
    public static final Environment Develop = new Environment("Develop");
    
    /**
     * 测试环境
     */
    public static final Environment Test = new Environment("Test");
    
    /**
     * 生产环境
     */
    public static final Environment Produce = new Environment("Produce");
}
