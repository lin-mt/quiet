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

package com.gitee.quiet.system.manager;

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.repository.QuietDeptRepository;
import com.gitee.quiet.system.service.QuietDeptUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDeptManager {

  private final QuietDeptRepository deptRepository;

  private final QuietDeptUserService deptUserService;

  /**
   * 删除部门数据
   *
   * @param deleteId 要删除的部门ID
   */
  public void deleteById(@NotNull Long deleteId) {
    if (CollectionUtils.isNotEmpty(deptRepository.findAllByParentId(deleteId))) {
      throw new ServiceException("dept.has.children.can.not.deleted");
    }
    if (CollectionUtils.isNotEmpty(deptUserService.listAllByDeptId(deleteId))) {
      throw new ServiceException("dept.has.member.can.not.deleted");
    }
    deptRepository.deleteById(deleteId);
  }
}
