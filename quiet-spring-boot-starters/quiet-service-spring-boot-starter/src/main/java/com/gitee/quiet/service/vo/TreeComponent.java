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
 * <a href="https://ant.design/components/tree-cn/">前端树型控件</a>
 *
 * @param <K> key类型
 * @param <T> 子级类型
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
public interface TreeComponent<K, T> {
    
    /**
     * 选项标题
     *
     * @return 显示的选项标题
     */
    String getTitle();
    
    /**
     * 组件 key
     *
     * @return 组件key
     */
    K getKey();
    
    /**
     * 子级选项
     *
     * @return 子级选项
     */
    List<T> getChildren();
}
