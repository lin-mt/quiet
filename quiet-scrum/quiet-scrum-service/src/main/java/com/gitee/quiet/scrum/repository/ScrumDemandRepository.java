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
import com.gitee.quiet.scrum.entity.ScrumDemand;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 需求repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumDemandRepository extends QuietRepository<ScrumDemand> {

    /**
     * 根据迭代ID查询该迭代的所有需求
     *
     * @param iterationId 迭代ID
     * @return 迭代中的所有需求
     */
    List<ScrumDemand> findAllByIterationId(Long iterationId);

    /**
     * 根据项目ID和需求标题查询需求信息
     *
     * @param projectId 项目ID
     * @param title     需求标题
     * @return 需求信息
     */
    ScrumDemand findByProjectIdAndTitle(Long projectId, String title);

    /**
     * 根据项目ID批量查询需求信息
     *
     * @param projectId 项目ID
     * @return 项目的所有需求信息
     */
    List<ScrumDemand> findAllByProjectId(Long projectId);

    /**
     * 根据优先级ID统计处于该优先级的需求有多少
     *
     * @param priorityId 优先级ID
     * @return 处于该优先级的需求数量
     */
    long countByPriorityId(Long priorityId);

    /**
     * 根据迭代ID统计处于该迭代的需求数量
     *
     * @param iterationId 迭代ID
     * @return 处于该迭代的需求数量
     */
    long countByIterationId(Long iterationId);
}
