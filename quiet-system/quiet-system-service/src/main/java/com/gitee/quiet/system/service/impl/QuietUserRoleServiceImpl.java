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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietUserRoleRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietUserRoleService;
import com.gitee.quiet.system.service.QuietUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户-角色 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietUserRoleServiceImpl implements QuietUserRoleService {

  private final QuietUserRoleRepository userRoleRepository;

  private final QuietUserService userService;

  private final QuietRoleService roleService;

  public QuietUserRoleServiceImpl(
      QuietUserRoleRepository userRoleRepository,
      @Lazy QuietUserService userService,
      QuietRoleService roleService) {
    this.userRoleRepository = userRoleRepository;
    this.userService = userService;
    this.roleService = roleService;
  }

  @Override
  public QuietUserRole saveOrUpdate(@NotNull QuietUserRole userRole) {
    if (!userService.existsById(userRole.getUserId())) {
      throw new ServiceException("userRole.user.id.no.exist", userRole.getUserId());
    }
    if (!roleService.existsById(userRole.getRoleId())) {
      throw new ServiceException("userRole.role.id.no.exist", userRole.getRoleId());
    }
    Optional<QuietUserRole> exist =
        userRoleRepository.findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
    exist.ifPresent(ur -> userRole.setId(ur.getId()));
    return userRoleRepository.save(userRole);
  }

  @Override
  public void deleteByIds(@NotNull @NotEmpty List<Long> ids) {
    userRoleRepository.deleteByIdIn(ids);
  }

  @Override
  public List<QuietUserRole> findByUserId(@NotNull Long userId) {
    return userRoleRepository.findByUserId(userId);
  }

  @Override
  public void deleteByUserId(@NotNull Long userId) {
    userRoleRepository.deleteByUserId(userId);
  }

  @Override
  public List<QuietUserRole> findRolesByUserIds(@NotNull @NotEmpty Collection<Long> userIds) {
    return userRoleRepository.findByUserIdIn(userIds);
  }

  @Override
  public void deleteUserRole(@NotNull Long userId, @NotNull Long roleId) {
    userRoleRepository.deleteByUserIdAndRoleId(userId, roleId);
  }

  @Override
  public List<QuietUserRole> addRoles(List<QuietUserRole> userRoles) {
    Map<Long, List<QuietUserRole>> userIdToRoles =
        userRoles.stream().collect(Collectors.groupingBy(QuietUserRole::getUserId));
    for (Map.Entry<Long, List<QuietUserRole>> entry : userIdToRoles.entrySet()) {
      List<QuietUserRole> roles = userRoleRepository.findByUserId(entry.getKey());
      if (CollectionUtils.isNotEmpty(roles)) {
        Iterator<QuietUserRole> iterator = userRoles.iterator();
        while (iterator.hasNext()) {
          QuietUserRole next = iterator.next();
          for (QuietUserRole has : roles) {
            if (next.getUserId().equals(has.getUserId())
                && next.getRoleId().equals(has.getRoleId())) {
              iterator.remove();
            }
          }
        }
      }
    }
    if (CollectionUtils.isNotEmpty(userRoles)) {
      return userRoleRepository.saveAll(userRoles);
    }
    return List.of();
  }
}
