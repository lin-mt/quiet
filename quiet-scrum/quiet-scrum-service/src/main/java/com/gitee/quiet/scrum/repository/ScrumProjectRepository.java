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
import com.gitee.quiet.scrum.entity.ScrumProject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumProjectRepository extends QuietRepository<ScrumProject> {

  /**
   * 根据项目名称和负责经理查询项目信息
   *
   * @param name 项目名称
   * @param manager 项目经理ID
   * @return 项目信息
   */
  ScrumProject findByNameAndManager(String name, Long manager);

  /**
   * 根据项目经理ID查询负责的项目信息
   *
   * @param manager 项目经理ID
   * @return 项目信息
   */
  List<ScrumProject> findAllByManager(Long manager);

  /**
   * 统计多少项目用了指定的模板
   *
   * @param templateId 统计的模板ID
   * @return 使用了该模板的项目数量
   */
  long countByTemplateId(Long templateId);
}
