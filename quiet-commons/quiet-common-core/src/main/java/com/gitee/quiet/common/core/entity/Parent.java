/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.common.core.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 父子关系.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Parent<T> {

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
        if (null == getChildren()) {
            setChildren(new ArrayList<>());
            if (getChildren() == null) {
                throw new IllegalStateException("设置子级信息后仍为 null");
            }
        }
        getChildren().add(children);
    }
}
