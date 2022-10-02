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

import com.gitee.quiet.scrum.entity.ScrumTemplate;

import java.util.List;
import java.util.Set;

/**
 * 模板信息Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTemplateService {

  /**
   * 根据模板ID批量查询模板信息
   *
   * @param ids 模板ID集合
   * @return 模板信息
   */
  List<ScrumTemplate> findAllByIds(Set<Long> ids);

  /**
   * 根据模板ID获取模板信息
   *
   * @param id 模板ID
   * @return 模板信息
   */
  ScrumTemplate findById(Long id);

  /**
   * 根据模板id、名称、状态查询模板信息
   *
   * @param id 模板ID
   * @param name 模板名称
   * @param enabled 模板状态
   * @param limit 查询数量，默认查询 15 条数据，传 0 则查询所有
   * @return 模板信息
   */
  List<ScrumTemplate> list(Long id, String name, Boolean enabled, Long limit);

  /**
   * 保存或更新模板，新增时启用状态默认为 false，更新时不更新启用状态
   *
   * @param entity 模板信息
   * @return 保存或更新后的模板信息
   */
  ScrumTemplate saveOrUpdate(ScrumTemplate entity);
}
