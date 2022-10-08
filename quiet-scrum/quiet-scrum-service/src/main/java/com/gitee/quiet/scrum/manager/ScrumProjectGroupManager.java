/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.scrum.manager;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.repository.ScrumProjectGroupRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumProjectGroupManager {

  private final ScrumProjectService projectService;
  private final ScrumProjectGroupRepository projectGroupRepository;

  /**
   * 根据ID删除项目组
   *
   * @param id 项目组ID
   */
  public void delete(Long id) {
    List<ScrumProject> projects = projectService.list(id);
    if (CollectionUtils.isNotEmpty(projects)) {
      throw new ServiceException("projectGroup.canNot.delete.projectExist");
    }
    projectGroupRepository.deleteById(id);
  }
}
