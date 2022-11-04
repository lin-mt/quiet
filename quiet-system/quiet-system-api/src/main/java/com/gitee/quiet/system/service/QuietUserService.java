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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * 用户 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietUserService {

  /**
   * 新增用户.
   *
   * @param quietUser 用户信息
   * @return true：保存成功 false：保存失败
   */
  QuietUser save(QuietUser quietUser);

  /**
   * 更新用户信息.
   *
   * @param user 要更新的用户信息
   * @return 更新后的用户信息
   */
  QuietUser update(QuietUser user);

  /**
   * 根据实体数据查询.
   *
   * @param params 查询参数
   * @param page 分页参数
   * @return 查询结果
   */
  Page<QuietUser> page(QuietUser params, Pageable page);

  /**
   * 判断该用户 ID 是否存在
   *
   * @param userId 用户ID
   * @return true：存在，false：不存在
   */
  boolean existsById(Long userId);

  /**
   * 根据用户ID批量查询信息
   *
   * @param userIds 用户ID集合
   * @return 用户信息
   */
  List<QuietUser> findByUserIds(Set<Long> userIds);

  /**
   * 根据用户名/全名查询用户信息
   *
   * @param name 用户名/全名
   * @param userIds 用户ID集合
   * @param limit 查询多少用户信息，小于1则查询所有
   * @return 用户信息
   */
  List<QuietUser> listUsers(String name, Set<Long> userIds, int limit);

  /**
   * 根据用户ID获取用户信息
   *
   * @param id 用户ID
   * @return 用户信息
   */
  QuietUser findById(Long id);

  /**
   * 根据团队ID获取团队成员信息
   *
   * @param id 团队ID
   * @return 团队成员信息
   */
  List<QuietUser> listTeamUser(Long id);

  /**
   * 根据用户名集合查询用户信息
   *
   * @param names 用户名集合
   * @return 用户信息
   */
  List<QuietUser> findByUsernames(Set<String> names);
}
