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

import com.gitee.quiet.system.entity.QuietDepartmentUser;
import com.gitee.quiet.system.repository.QuietDepartmentUserRepository;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门成员信息 service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDepartmentUserServiceImpl implements QuietDepartmentUserService {

  private final QuietDepartmentUserRepository departmentUserRepository;

  public QuietDepartmentUserServiceImpl(QuietDepartmentUserRepository departmentUserRepository) {
    this.departmentUserRepository = departmentUserRepository;
  }

  @Override
  public List<QuietDepartmentUser> listAllByDepartmentId(@NotNull Long departmentId) {
    return departmentUserRepository.findAllByDepartmentId(departmentId);
  }

  @Override
  public void deleteByUserId(@NotNull Long userId) {
    departmentUserRepository.deleteByUserId(userId);
  }

  @Override
  public void addUsers(@NotNull Long departmentId, @NotNull Set<Long> userIds) {
    List<QuietDepartmentUser> departmentUsers = this.listAllByDepartmentId(departmentId);
    if (CollectionUtils.isNotEmpty(departmentUsers)) {
      Set<Long> existUserIds =
          departmentUsers.stream().map(QuietDepartmentUser::getUserId).collect(Collectors.toSet());
      userIds.removeAll(existUserIds);
    }
    if (CollectionUtils.isNotEmpty(userIds)) {
      List<QuietDepartmentUser> newUserInfo = new ArrayList<>(userIds.size());
      for (Long userId : userIds) {
        newUserInfo.add(new QuietDepartmentUser(departmentId, userId));
      }
      departmentUserRepository.saveAll(newUserInfo);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeUsers(@NotNull Long departmentId, @NotNull @NotEmpty Set<Long> userIds) {
    departmentUserRepository.deleteAllByDepartmentIdAndUserIdIsIn(departmentId, userIds);
  }
}
