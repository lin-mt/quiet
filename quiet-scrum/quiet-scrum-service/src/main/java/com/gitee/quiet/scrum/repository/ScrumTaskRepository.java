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
import com.gitee.quiet.scrum.entity.ScrumTask;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 任务repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTaskRepository extends QuietRepository<ScrumTask> {

  /**
   * 根据需求ID和任务标题查询任务信息
   *
   * @param demandId 需求ID
   * @param title 任务标题
   * @return 任务信息
   */
  ScrumTask findByDemandIdAndTitle(Long demandId, String title);

  /**
   * 根据需求ID查询所有任务信息
   *
   * @param demandId 需求ID
   * @return 需求下的所有任务信息
   */
  List<ScrumTask> findAllByDemandId(Long demandId);

  /**
   * 根据任务步骤ID集合统计在该集合下的任务数量
   *
   * @param taskStepIds 任务步骤ID集合
   * @return 任务数量
   */
  Long countByTaskStepIdIn(Set<Long> taskStepIds);
}
