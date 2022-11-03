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
import com.gitee.quiet.system.entity.QuietPermission;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.repository.QuietRoleRepository;
import com.gitee.quiet.system.service.QuietPermissionService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietRoleManager {

  private final QuietRoleRepository roleRepository;
  private final QuietPermissionService permissionService;

  /**
   * 根据角色 ID 删除角色
   *
   * @param deleteId 要删除的角色 ID
   */
  public void deleteRole(@NotNull Long deleteId) {
    Optional<QuietRole> exist = roleRepository.findById(deleteId);
    if (exist.isEmpty()) {
      throw new ServiceException("role.not.exist");
    }
    List<QuietRole> children =
            roleRepository.findByParentIdIn(Collections.singleton(exist.get().getId()));
    if (CollectionUtils.isNotEmpty(children)) {
      throw new ServiceException("role.can.not.delete.has.children");
    }
    List<QuietPermission> permissions = permissionService.listByRoleId(deleteId);
    if (CollectionUtils.isNotEmpty(permissions)) {
      throw new ServiceException("role.can.not.delete.has.permission.config");
    }
    roleRepository.deleteById(deleteId);
  }
}
