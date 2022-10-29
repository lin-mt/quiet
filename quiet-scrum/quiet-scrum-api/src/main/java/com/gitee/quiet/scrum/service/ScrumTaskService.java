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

import com.gitee.quiet.scrum.entity.ScrumTask;

import java.util.List;
import java.util.Set;

/**
 * 任务信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTaskService {

  /**
   * 查询任务信息
   *
   * @param demandIds 需求ID集合
   * @param executorIds 执行者ID集合
   * @return 任务集合
   */
  List<ScrumTask> list(Set<Long> demandIds, Set<Long> executorIds);

  /**
   * 根据任务ID删除任务信息
   *
   * @param id 任务ID
   */
  void deleteById(Long id);

  /**
   * 根据需求ID查询任务信息
   *
   * @param demandId 需求ID
   * @return 任务信息
   */
  List<ScrumTask> listAllByDemandId(Long demandId);

  /**
   * 根据任务步骤ID集合统计在该集合下的任务数量
   *
   * @param taskStepIds 任务步骤ID集合
   * @return 任务数量
   */
  Long countByTaskStepIdIn(Set<Long> taskStepIds);
}
