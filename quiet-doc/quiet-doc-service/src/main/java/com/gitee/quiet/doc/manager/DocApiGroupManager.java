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

import com.gitee.quiet.doc.repository.DocApiGroupRepository;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocApiGroupManager {

  private final DocApiGroupRepository apiGroupRepository;
  private final DocApiService apiService;

  /**
   * 根据接口分组ID删除信息
   *
   * @param id 要删除的接口分组ID
   */
  public void deleteById(Long id) {
    apiGroupRepository
            .findById(id)
            .orElseThrow(() -> new ServiceException("api.group.id.notExist", id));
    apiService.removeGroup(id);
    apiGroupRepository.deleteById(id);
  }
}
