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
import com.gitee.quiet.scrum.entity.ScrumPriority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 优先级repository
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumPriorityRepository extends QuietRepository<ScrumPriority> {

    /**
     * 根据模板ID和优先级名称查询优先级信息
     *
     * @param templateId 模板ID
     * @param name       优先级名称
     * @return 优先级信息
     */
    ScrumPriority findByTemplateIdAndName(Long templateId, String name);

    /**
     * 根据模板ID查询优先级信息
     *
     * @param templateId 模板ID
     * @return 优先级信息
     */
    List<ScrumPriority> findAllByTemplateId(Long templateId);

    /**
     * 根据模板ID集合批量查询所有的优先级配置
     *
     * @param templateIds 模板ID集合
     * @return 所有优先级配置信息
     */
    List<ScrumPriority> findAllByTemplateIdIn(Set<Long> templateIds);

    /**
     * 根据模板ID统计优先级数量
     *
     * @param templateId 模板ID
     * @return 优先级数量
     */
    long countByTemplateId(Long templateId);
}
