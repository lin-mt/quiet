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

import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietUserRoleRepository;
import com.gitee.quiet.system.service.QuietUserRoleService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
@AllArgsConstructor
public class QuietUserRoleServiceImpl implements QuietUserRoleService {

  private final QuietUserRoleRepository userRoleRepository;

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

  @Override
  public void updateRoles(Long userId, Set<Long> roleIds) {
    if (CollectionUtils.isEmpty(roleIds)) {
      userRoleRepository.deleteByUserId(userId);
      return;
    }
    List<QuietUserRole> userRoles = userRoleRepository.findByUserId(userId);
    Set<Long> existRole = new HashSet<>();
    if (CollectionUtils.isNotEmpty(userRoles)) {
      Iterator<QuietUserRole> iterator = userRoles.iterator();
      while (iterator.hasNext()) {
        QuietUserRole userRole = iterator.next();
        if (roleIds.contains(userRole.getRoleId())) {
          existRole.add(userRole.getRoleId());
          iterator.remove();
        }
      }
    }
    if (CollectionUtils.isNotEmpty(userRoles)) {
      userRoleRepository.deleteAll(userRoles);
    }
    roleIds.removeAll(existRole);
    if (CollectionUtils.isNotEmpty(roleIds)) {
      List<QuietUserRole> newRoles = new ArrayList<>();
      for (Long roleId : roleIds) {
        QuietUserRole quietUserRole = new QuietUserRole();
        quietUserRole.setUserId(userId);
        quietUserRole.setRoleId(roleId);
        newRoles.add(quietUserRole);
      }
      userRoleRepository.saveAll(newRoles);
    }
  }
}
