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

package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumIteration;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 迭代信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumIterationService {

  /**
   * 根据版本ID集合批量查询迭代信息
   *
   * @param versionIds 要查询的版本ID集合
   * @return 指定版本中所有的迭代信息
   */
  List<ScrumIteration> findAllByVersionIds(Set<Long> versionIds);

  /**
   * 根据版本ID统计处于该版本下有多少迭代数量
   *
   * @param versionId 版本ID
   * @return 处于该版本下的迭代数量
   */
  long countByVersionId(@NotNull Long versionId);

  /**
   * 根据id查询迭代信息
   *
   * @param id 迭代id
   * @return 迭代信息
   */
  ScrumIteration getById(Long id);
}
