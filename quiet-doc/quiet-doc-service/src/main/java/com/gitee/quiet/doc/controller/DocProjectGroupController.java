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

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocProjectGroupConverter;
import com.gitee.quiet.doc.converter.DocProjectGroupMemberConverter;
import com.gitee.quiet.doc.dto.DocProjectGroupDTO;
import com.gitee.quiet.doc.dto.DocProjectGroupMemberDTO;
import com.gitee.quiet.doc.dubbo.DubboUserService;
import com.gitee.quiet.doc.entity.DocProjectGroup;
import com.gitee.quiet.doc.entity.DocProjectGroupMember;
import com.gitee.quiet.doc.manager.DocProjectGroupManager;
import com.gitee.quiet.doc.service.DocProjectGroupService;
import com.gitee.quiet.doc.vo.DocProjectGroupMemberVO;
import com.gitee.quiet.doc.vo.DocProjectGroupVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project-group")
public class DocProjectGroupController {

  private final DocProjectGroupService projectGroupService;
  private final DocProjectGroupManager projectGroupManager;
  private final DocProjectGroupConverter converter;
  private final DocProjectGroupMemberConverter groupMemberConverter;
  private final DubboUserService userService;

  /**
   * 查询所有项目分组.
   *
   * @param name 项目名称
   * @param groupIds 项目ID集合
   * @return 项目分组信息
   */
  @GetMapping("/all")
  public Result<List<DocProjectGroupVO>> all(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Set<Long> groupIds) {
    List<DocProjectGroup> projectGroups = projectGroupService.listAll(name, groupIds);
    return Result.success(converter.entities2vos(projectGroups));
  }

  /**
   * 查询项目分组所有成员.
   *
   * @param groupId 项目分组ID
   * @return 项目分组成员信息
   */
  @GetMapping("/all-member/{groupId}")
  public Result<List<DocProjectGroupMemberVO>> allMember(@PathVariable Long groupId) {
    List<DocProjectGroupMember> members = projectGroupManager.listMemberById(groupId);
    List<DocProjectGroupMemberVO> memberVos = groupMemberConverter.entities2vos(members);
    if (CollectionUtils.isNotEmpty(memberVos)) {
      Set<Long> userIds =
          memberVos.stream().map(DocProjectGroupMemberVO::getUserId).collect(Collectors.toSet());
      Map<Long, String> userId2Username =
          userService.findByUserIds(userIds).stream()
              .collect(Collectors.toMap(QuietUser::getId, QuietUser::getUsername));
      memberVos.forEach(member -> member.setUsername(userId2Username.get(member.getUserId())));
    }
    return Result.success(memberVos);
  }

  /**
   * 添加项目分组成员.
   *
   * @param dto 项目分组成员
   * @return 项目分组成员信息
   */
  @PostMapping("/add-member")
  public Result<DocProjectGroupMemberVO> addMember(
      @RequestBody @Validated(Create.class) DocProjectGroupMemberDTO dto) {
    DocProjectGroupMember projectGroupMember =
        projectGroupManager.saveOrUpdateMember(groupMemberConverter.dto2entity(dto));
    return Result.success(groupMemberConverter.entity2vo(projectGroupMember));
  }

  /**
   * 移除项目分组成员.
   *
   * @param groupId 分组ID
   * @param userId 成员ID
   * @return 移除结果
   */
  @DeleteMapping("/remove-member")
  public Result<Object> removeMember(@RequestParam Long groupId, @RequestParam Long userId) {
    projectGroupManager.removeMember(groupId, userId);
    return Result.deleteSuccess();
  }

  /**
   * 更新项目分组成员.
   *
   * @param dto 更新的项目成员信息
   * @return 更新结果
   */
  @PostMapping("/update-member")
  public Result<DocProjectGroupMemberVO> updateMember(
      @RequestBody @Validated(Update.class) DocProjectGroupMemberDTO dto) {
    DocProjectGroupMember projectGroupMember =
        projectGroupManager.saveOrUpdateMember(groupMemberConverter.dto2entity(dto));
    return Result.success(groupMemberConverter.entity2vo(projectGroupMember));
  }

  /**
   * 新增项目分组.
   *
   * @param dto 新增的项目分组信息
   * @return 新增后的项目分组信息
   */
  @PostMapping
  public Result<DocProjectGroupVO> save(
      @RequestBody @Validated(Create.class) DocProjectGroupDTO dto) {
    DocProjectGroup save = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(save));
  }

  /**
   * 删除项目分组.
   *
   * @param id 删除的项目分组ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectGroupManager.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新项目分组.
   *
   * @param dto 更新的项目分组信息
   * @return 更新后的项目分组信息
   */
  @PutMapping
  public Result<DocProjectGroupVO> update(
      @RequestBody @Validated(Update.class) DocProjectGroupDTO dto) {
    DocProjectGroup update = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }
}
