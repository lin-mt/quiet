/*
 * Copyright 2020 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.constant.RoleNames;
import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietTeamUserRole;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.params.QuietTeamParam;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
import com.querydsl.core.QueryResults;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/team")
public class QuietTeamController {
    
    private final QuietTeamService teamService;
    
    private final QuietTeamUserRoleService userTeamRoleService;
    
    private final QuietTeamUserService teamUserService;
    
    private final QuietRoleService roleService;
    
    private final QuietUserService userService;
    
    public QuietTeamController(QuietTeamService teamService, QuietTeamUserRoleService userTeamRoleService,
            QuietTeamUserService teamUserService, QuietRoleService roleService, QuietUserService userService) {
        this.teamService = teamService;
        this.userTeamRoleService = userTeamRoleService;
        this.teamUserService = teamUserService;
        this.roleService = roleService;
        this.userService = userService;
    }
    
    /**
     * 分页查询团队信息.
     *
     * @return 团队信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietTeam>> page(@RequestBody QuietTeamParam param) {
        QueryResults<QuietTeam> result = teamService.page(param.getParams(), param.page());
        if (CollectionUtils.isNotEmpty(result.getResults())) {
            Set<Long> teamIds = result.getResults().stream().map(QuietTeam::getId).collect(Collectors.toSet());
            List<QuietTeamUserRole> userTeamRoles = userTeamRoleService.findByTeamIds(teamIds);
            Set<Long> allUserIds = teamUserService.findAllUsersByTeamIds(teamIds).stream().map(QuietTeamUser::getUserId)
                    .collect(Collectors.toSet());
            Map<Long, QuietUser> userIdToUserInfo = userService.findByUserIds(allUserIds).stream()
                    .collect(Collectors.toMap(QuietUser::getId, u -> u));
            Map<Long, List<QuietTeamUser>> teamIdToTeamUsers = teamUserService.mapTeamIdToTeamUsers(teamIds);
            Map<Long, List<QuietTeamUserRole>> teamIdToUserRoles = userTeamRoles.stream()
                    .collect(Collectors.groupingBy(QuietTeamUserRole::getTeamId));
            QuietRole productOwner = roleService.findByRoleName(RoleNames.ProductOwner);
            QuietRole scrumMaster = roleService.findByRoleName(RoleNames.ScrumMaster);
            for (QuietTeam quietTeam : result.getResults()) {
                List<QuietTeamUser> quietTeamUsers = teamIdToTeamUsers.get(quietTeam.getId());
                if (CollectionUtils.isNotEmpty(quietTeamUsers)) {
                    List<QuietUser> members = new ArrayList<>(quietTeamUsers.size());
                    for (QuietTeamUser quietTeamUser : quietTeamUsers) {
                        members.add(userIdToUserInfo.get(quietTeamUser.getUserId()));
                    }
                    quietTeam.setMembers(members);
                }
                List<QuietTeamUserRole> quietTeamUserRoles = teamIdToUserRoles.get(quietTeam.getId());
                if (CollectionUtils.isNotEmpty(quietTeamUserRoles)) {
                    List<QuietUser> teamProductOwner = new ArrayList<>();
                    List<QuietUser> teamScrumMaster = new ArrayList<>();
                    for (QuietTeamUserRole quietTeamUserRole : quietTeamUserRoles) {
                        if (quietTeamUserRole.getRoleId().equals(productOwner.getId())) {
                            teamProductOwner.add(userIdToUserInfo.get(quietTeamUserRole.getUserId()));
                        }
                        if (quietTeamUserRole.getRoleId().equals(scrumMaster.getId())) {
                            teamScrumMaster.add(userIdToUserInfo.get(quietTeamUserRole.getUserId()));
                        }
                    }
                    quietTeam.setProductOwner(teamProductOwner);
                    quietTeam.setScrumMaster(teamScrumMaster);
                }
            }
        }
        return Result.success(result);
    }
    
    /**
     * 新增团队.
     *
     * @param param :save 新增的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping("/save")
    public Result<QuietTeam> save(@RequestBody @Validated(Create.class) QuietTeamParam param) {
        return Result.createSuccess(teamService.saveOrUpdate(param.getSave()));
    }
    
    /**
     * 删除团队.
     *
     * @param param :deleteId 删除的团队ID
     * @return Result
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietTeamParam param) {
        teamService.deleteTeam(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新团队.
     *
     * @param param :update 更新的团队信息
     * @return 新增后的团队信息
     */
    @PostMapping("/update")
    public Result<QuietTeam> update(@RequestBody @Validated(Update.class) QuietTeamParam param) {
        return Result.updateSuccess(teamService.saveOrUpdate(param.getUpdate()));
    }
    
}
