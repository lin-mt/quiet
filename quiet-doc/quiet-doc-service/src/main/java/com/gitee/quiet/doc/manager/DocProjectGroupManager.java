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

import com.gitee.quiet.doc.dubbo.DubboUserService;
import com.gitee.quiet.doc.entity.DocProject;
import com.gitee.quiet.doc.entity.DocProjectGroupMember;
import com.gitee.quiet.doc.enums.Permission;
import com.gitee.quiet.doc.repository.DocProjectGroupMemberRepository;
import com.gitee.quiet.doc.repository.DocProjectGroupRepository;
import com.gitee.quiet.doc.repository.DocProjectRepository;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectGroupManager {

  private final DocProjectGroupRepository projectGroupRepository;

  private final DocProjectRepository projectRepository;

  private final DocProjectGroupMemberRepository memberRepository;

  private final DubboUserService userService;

  /**
   * 根据项目分组ID删除分组信息
   *
   * @param id 项目分组ID
   */
  public void delete(Long id) {
    List<DocProject> docProjects = projectRepository.findAllByGroupId(id);
    if (CollectionUtils.isNotEmpty(docProjects)) {
      throw new ServiceException("projectGroup.has.docProject.can.not.delete");
    }
    projectGroupRepository.deleteById(id);
  }

  /**
   * 根据分组ID查询成员信息
   *
   * @param groupId 分组ID
   * @return 成员信息
   */
  public List<DocProjectGroupMember> listMemberById(Long groupId) {
    if (groupId == null) {
      return List.of();
    }
    return memberRepository.findAllByGroupId(groupId);
  }

  /**
   * 新增分组成员
   *
   * @param entity 新增的分组成员信息
   * @return 新增后的分组成员信息
   */
  public DocProjectGroupMember saveOrUpdateMember(DocProjectGroupMember entity) {
    Long userId = entity.getUserId();
    Long groupId = entity.getGroupId();
    QuietUser user = userService.getById(userId);
    if (user == null) {
      throw new ServiceException("user.not.exist", userId);
    }
    projectGroupRepository
        .findById(groupId)
        .orElseThrow(() -> new ServiceException("projectGroup.not.exist", groupId));
    DocProjectGroupMember exist = memberRepository.findByGroupIdAndUserId(groupId, userId);
    if (exist != null) {
      if (Permission.GROUP_LEADER.equals(exist.getPermission())
          && !Permission.GROUP_LEADER.equals(entity.getPermission())) {
        long groupLeaderCount =
            memberRepository.countByGroupIdAndPermission(groupId, Permission.GROUP_LEADER);
        if (groupLeaderCount == 1L) {
          throw new ServiceException("projectGroup.groupLeader.can.not.empty");
        }
      }
      exist.setPermission(entity.getPermission());
      return memberRepository.saveAndFlush(exist);
    }
    return memberRepository.saveAndFlush(entity);
  }

  /**
   * 移除项目分组成员.
   *
   * @param groupId 分组ID
   * @param userId 成员ID
   */
  public void removeMember(Long groupId, Long userId) {
    DocProjectGroupMember projectGroupMember =
        memberRepository.findByGroupIdAndUserId(groupId, userId);
    if (projectGroupMember == null) {
      throw new ServiceException("projectGroup.not.member", groupId, userId);
    }
    if (Permission.GROUP_LEADER.equals(projectGroupMember.getPermission())) {
      long groupLeaderCount =
          memberRepository.countByGroupIdAndPermission(groupId, Permission.GROUP_LEADER);
      if (groupLeaderCount == 1L) {
        throw new ServiceException("projectGroup.groupLeader.can.not.empty");
      }
    }
    memberRepository.delete(projectGroupMember);
  }
}
