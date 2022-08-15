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
 * <a href="https://ant.design/components/tree-cn/">前端树型控件</a>
 *
 * @param <K> key类型
 * @param <T> 子级类型
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface TreeComponentVO<K, T> {

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
