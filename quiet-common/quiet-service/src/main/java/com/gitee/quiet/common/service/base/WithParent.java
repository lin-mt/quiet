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

package com.gitee.quiet.common.service.base;

import com.gitee.quiet.common.service.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 父子关系.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface WithParent<T extends WithParent<T>> {
    
    /**
     * 获取父的ID
     *
     * @return 父ID
     */
    Long getParentId();
    
    /**
     * 获取子级信息
     *
     * @return 子级信息集合
     */
    List<T> getChildren();
    
    /**
     * 设置子级集合
     *
     * @param children 子级集合
     */
    void setChildren(List<T> children);
    
    /**
     * 添加子级信息
     *
     * @param children 子级信息
     */
    default void addChildren(T children) {
        if (CollectionUtils.isEmpty(getChildren())) {
            setChildren(new ArrayList<>());
            if (CollectionUtils.isEmpty(getChildren())) {
                throw new ServiceException("设置子级集合后仍然为空");
            }
        }
        getChildren().add(children);
    }
}