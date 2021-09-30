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

package com.gitee.quiet.service.vo.front;

/**
 * <a href="https://ant.design/components/select-cn/">前端下拉选择器</a>
 *
 * @param <V> 值类型
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
public interface SelectVO<V> {
    
    /**
     * 选中的值
     *
     * @return 用户选中的值
     */
    V getValue();
    
    /**
     * 展示的值
     *
     * @return 组件展示给用户的值
     */
    String getLabel();
}
