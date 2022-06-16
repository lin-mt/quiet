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
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 任务步骤repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTaskStepRepository extends QuietRepository<ScrumTaskStep> {

    /**
     * 根据模板ID和步骤名称查询步骤信息
     *
     * @param templateId 模板ID
     * @param name       任务步骤名称
     * @return 任务步骤信息
     */
    ScrumTaskStep findByTemplateIdAndName(Long templateId, String name);

    /**
     * 根据模板ID查询所有任务步骤
     *
     * @param templateId 模板ID
     * @return 任务步骤信息
     */
    List<ScrumTaskStep> findAllByTemplateId(Long templateId);

    /**
     * 根据模板ID集合查询所有任务步骤
     *
     * @param templateIds 模板ID集合
     * @return 所有任务步骤信息
     */
    List<ScrumTaskStep> findAllByTemplateIdIn(Set<Long> templateIds);

    /**
     * 根据模板ID统计该模板下任务有多少步骤
     *
     * @param templateId 模板ID
     * @return 任务步骤数
     */
    long countByTemplateId(Long templateId);
}
