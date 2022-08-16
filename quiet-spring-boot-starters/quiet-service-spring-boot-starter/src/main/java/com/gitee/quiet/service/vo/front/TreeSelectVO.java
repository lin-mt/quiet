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

package com.gitee.quiet.service.vo.front;

import java.util.List;

/**
 * <a href="https://ant.design/components/tree-select-cn/">前端树型选择器</a>
 *
 * @param <V> 值类型
 * @param <T> 子级类型
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface TreeSelectVO<V, T extends TreeSelectVO<V, T>> {

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

  default V getKey() {
    return getValue();
  }
}
