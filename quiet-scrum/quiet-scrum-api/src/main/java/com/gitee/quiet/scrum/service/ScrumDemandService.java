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

import com.gitee.quiet.scrum.entity.ScrumDemand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 需求service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumDemandService {

  /**
   * 根据迭代ID查询需求，迭代ID为空，limit 则不超过 30
   *
   * @param iterationId 迭代ID
   * @param limit 查询数量
   * @return 需求信息
   */
  List<ScrumDemand> list(Long iterationId, Long limit);

  /**
   * 分页查询需求信息
   *
   * @param params 查询参数
   * @param planned 是否已规划，true：已规划，false：待规划
   * @param page 分页参数
   * @return 需求信息
   */
  Page<ScrumDemand> page(ScrumDemand params, Boolean planned, Pageable page);

  /**
   * 创建需求
   *
   * @param save 新需求
   * @return 创建后的需求信息
   */
  ScrumDemand saveOrUpdate(@NotNull ScrumDemand save);

  /**
   * 根据优先级ID统计处于该优先级的需求数量
   *
   * @param priorityId 优先级ID
   * @return 处于该优先级的需求数量
   */
  long countByPriorityId(@NotNull Long priorityId);

  /**
   * 根据迭代ID统计处于该迭代的需求数量
   *
   * @param iterationId 迭代ID
   * @return 处于该迭代的需求数量
   */
  long countByIterationId(@NotNull Long iterationId);

  /**
   * 根据ID删除需求
   *
   * @param id 需求ID
   */
  void deleteById(Long id);

  /**
   * 校验是否需求ID是否存在
   *
   * @param id 需求ID
   */
  void checkIdExist(Long id);

  /**
   * 查询指定迭代未完成的所有需求
   *
   * @param iterationId 迭代ID
   * @return 未完成的需求信息
   */
  List<ScrumDemand> findAllUnfinished(Long iterationId);

  /**
   * 批量保存需求信息
   *
   * @param demands 需求信息集合
   */
  void saveAll(List<ScrumDemand> demands);

  /**
   * 根据项目ID删除所有需求信息
   *
   * @param projectId 项目ID
   */
  void deleteAllByProjectId(Long projectId);
}
