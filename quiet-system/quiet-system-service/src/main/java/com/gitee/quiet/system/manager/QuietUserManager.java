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

import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.entity.QuietUserRole;
import com.gitee.quiet.system.repository.QuietUserRepository;
import com.gitee.quiet.system.service.*;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietUserManager implements UserDetailsService {

  private final QuietUserRepository userRepository;
  private final QuietUserRoleService userRoleService;
  private final QuietRoleService roleService;
  private final QuietDeptUserService deptUserService;
  private final QuietTeamUserService teamUserService;
  private final QuietTeamUserRoleService teamUserRoleService;

  @Override
  public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
    QuietUser user = userRepository.getByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    List<QuietUserRole> quietUserRoles = userRoleService.findByUserId(user.getId());
    if (CollectionUtils.isNotEmpty(quietUserRoles)) {
      Set<Long> roleIds =
          quietUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
      List<QuietRole> roles =
          roleService.getReachableGrantedAuthorities(roleService.findAllById(roleIds));
      user.setAuthorities(roles);
    }
    return user;
  }

  /**
   * 删除用户.
   *
   * @param userId 要删除的用户的ID
   * @return true：删除成功
   */
  public boolean delete(@NotNull Long userId) {
    // 删除用户-角色信息
    userRoleService.deleteByUserId(userId);
    // 删除部门-用户信息
    deptUserService.deleteByUserId(userId);
    // 删除团队-用户信息
    List<QuietTeamUser> allTeamUser = teamUserService.findAllByUserId(userId);
    if (CollectionUtils.isNotEmpty(allTeamUser)) {
      teamUserRoleService.deleteByTeamUserIds(
          allTeamUser.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
    }
    teamUserService.deleteByUserId(userId);
    // TODO 删除跟用户相关的其他信息
    // 删除用户信息
    userRepository.deleteById(userId);
    return true;
  }
}
