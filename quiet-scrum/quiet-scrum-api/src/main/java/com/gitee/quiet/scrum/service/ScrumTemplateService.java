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
import com.gitee.quiet.scrum.vo.AllTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 模板信息Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumTemplateService {

  /**
   * 查询所有的模板信息
   *
   * @return 根据是否创建人创建的模板进行分组
   */
  AllTemplate allTemplates();

  /**
   * 新增模板
   *
   * @param save 新增的模板信息
   * @return 新增后的模板信息
   */
  ScrumTemplate save(ScrumTemplate save);

  /**
   * 更新模板
   *
   * @param update 更新的模板信息
   * @return 更新后的模板信息
   */
  ScrumTemplate update(ScrumTemplate update);

  /**
   * 根据模板ID删除模板
   *
   * @param id 要删除的模板的ID
   */
  void deleteById(@NotNull Long id);

  /**
   * 根据模板名称查询模板信息
   *
   * @param name 模板名称
   * @param limit 查询的模板数量
   * @return 查询结果
   */
  List<ScrumTemplate> listEnabledByName(String name, long limit);

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
   * 判断模板ID是否存在
   *
   * @param id 模板ID
   * @return true：存在，false：不存在
   */
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  boolean existsById(Long id);

  /**
   * 查询模板信息，包含步骤配置和优先级配置信息
   *
   * @param id 模板ID
   * @return 模板信息
   */
  ScrumTemplate templateInfo(Long id);
}
