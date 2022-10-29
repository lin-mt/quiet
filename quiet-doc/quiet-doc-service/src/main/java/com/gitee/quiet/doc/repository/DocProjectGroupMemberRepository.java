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

package com.gitee.quiet.doc.repository;

import com.gitee.quiet.doc.entity.DocProjectGroupMember;
import com.gitee.quiet.doc.enums.Permission;
import com.gitee.quiet.jpa.repository.QuietRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface DocProjectGroupMemberRepository extends QuietRepository<DocProjectGroupMember> {

  /**
   * 根据项目分组ID查询成员信息
   *
   * @param groupId 分组ID
   * @return 成员信息
   */
  List<DocProjectGroupMember> findAllByGroupId(Long groupId);

  /**
   * 根据分组ID和用户ID查询信息
   *
   * @param groupId 分组ID
   * @param userId 用户ID
   * @return 分组成员信息
   */
  DocProjectGroupMember findByGroupIdAndUserId(Long groupId, Long userId);

  /**
   * 统计项目组中权限人数
   *
   * @param groupId 项目组ID
   * @param permission 权限
   * @return 拥有该权限的人数
   */
  long countByGroupIdAndPermission(Long groupId, Permission permission);
}
