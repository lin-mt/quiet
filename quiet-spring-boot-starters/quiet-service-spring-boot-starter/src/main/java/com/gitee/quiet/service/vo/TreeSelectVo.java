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

package com.gitee.quiet.service.vo;

import java.util.List;

/**
 * <a href="https://ant.design/components/tree-select-cn/">前端树型选择器</a>
 *
 * @param <V> 值类型
 * @param <T> 子级类型
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface TreeSelectVo<V, T extends TreeSelectVo<V, T>> {
    
    /**
     * 标题
     *
     * @return 树型选择器选项的标题
     */
    String getTitle();
    
    /**
     * 值
     *
     * @return 用户选中的值
     */
    V getValue();
    
    /**
     * 子级选项
     *
     * @return 子级选项
     */
    List<T> getChildren();
}
