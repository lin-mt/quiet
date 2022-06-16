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
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模板Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTemplateRepository extends QuietRepository<ScrumTemplate> {

    /**
     * 查询启用的或者创建者为 creator 的模板信息
     *
     * @param enabled 是否启用
     * @param creator 创建者ID
     * @return 是否启用为enable或者创建者为creator创建模板信息
     */
    List<ScrumTemplate> findAllByEnabledOrCreator(boolean enabled, Long creator);

    /**
     * 根据模板名称查找模板信息
     *
     * @param name 模板名称
     * @return 模板信息
     */
    ScrumTemplate findByName(String name);
}
