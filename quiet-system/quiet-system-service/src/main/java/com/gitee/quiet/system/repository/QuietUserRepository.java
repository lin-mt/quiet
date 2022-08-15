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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietUser;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 查询用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietUserRepository extends QuietRepository<QuietUser> {

  /**
   * 根据用户名获取用户信息.
   *
   * @param username 用户名
   * @return 用户信息
   */
  QuietUser getByUsername(String username);

  /**
   * 批量根据用户ID查询用户信息
   *
   * @param userIds 用户ID集合
   * @return 用户信息
   */
  List<QuietUser> findByIdIsIn(Collection<Long> userIds);

  /**
   * 根据用户名批量查询
   *
   * @param usernames 用户名集合
   * @return 用户信息
   */
  List<QuietUser> findByUsernameIsIn(Set<String> usernames);
}
