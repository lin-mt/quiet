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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 迭代信息repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumIterationRepository extends QuietRepository<ScrumIteration> {

  /**
   * 根据版本ID查询迭代信息
   *
   * @param versionIds 要查询的版本ID集合
   * @return 所有迭代信息
   */
  List<ScrumIteration> findAllByVersionIdIn(Set<Long> versionIds);

  /**
   * 根据版本ID和迭代名称查询迭代信息
   *
   * @param versionId 版本ID
   * @param name 迭代名称
   * @return 迭代信息
   */
  ScrumIteration findByVersionIdAndName(Long versionId, String name);

  /**
   * 根据版本ID统计处于该版本下有多少迭代数量
   *
   * @param versionId 版本ID
   * @return 处于该版本下的迭代数量
   */
  long countByVersionId(Long versionId);

  /**
   * 根据版本ID查询版本下的所有迭代信息
   *
   * @param versionId 版本ID
   * @return 迭代信息
   */
  List<ScrumIteration> findAllByVersionId(Long versionId);
}
