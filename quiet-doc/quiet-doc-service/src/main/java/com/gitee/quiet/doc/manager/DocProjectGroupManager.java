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

package com.gitee.quiet.doc.manager;

import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.repository.DocProjectGroupRepository;
import com.gitee.quiet.doc.repository.DocProjectRepository;
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
public class DocProjectGroupManager {

  private final DocProjectGroupRepository projectGroupRepository;

  private final DocProjectRepository projectRepository;

  /**
   * 根据项目分组ID删除分组信息
   *
   * @param id 项目分组ID
   */
  public void delete(Long id) {
    List<DocProject> docProjects = projectRepository.findAllByGroupId(id);
    if (CollectionUtils.isNotEmpty(docProjects)) {
      throw new ServiceException("projectGroup.has.docProject.can.not.delete");
    }
    projectGroupRepository.deleteById(id);
  }
}
