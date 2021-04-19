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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.entity.ScrumTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有的模板信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class AllTemplate {
    
    /**
     * 登陆人创建的模板
     */
    private final List<ScrumTemplate> templateCreated = new ArrayList<>();
    
    /**
     * 非登陆人创建的可选的模板
     */
    private final List<ScrumTemplate> templateSelectable = new ArrayList<>();
    
    public List<ScrumTemplate> getTemplateCreated() {
        return templateCreated;
    }
    
    public List<ScrumTemplate> getTemplateSelectable() {
        return templateSelectable;
    }
    
}