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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.base.constant.RoleNames;
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.util.Where;
import com.gitee.quiet.system.entity.QuietRole;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietTeamUserRole;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietRoleService;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.system.entity.QQuietTeam.quietTeam;


/**
 * 团队 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietTeamServiceImpl implements QuietTeamService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietTeamRepository teamRepository;
    
    private final QuietTeamUserService teamUserService;
    
    private final QuietTeamUserRoleService teamUserRoleService;
    
    private final QuietRoleService roleService;
    
    private final QuietUserService userService;
    
    public QuietTeamServiceImpl(JPAQueryFactory jpaQueryFactory, QuietTeamRepository teamRepository,
            QuietTeamUserService teamUserService, QuietTeamUserRoleService teamUserRoleService,
            QuietRoleService roleService, QuietUserService userService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamRepository = teamRepository;
        this.teamUserService = teamUserService;
        this.teamUserRoleService = teamUserRoleService;
        this.roleService = roleService;
        this.userService = userService;
    }
    
    @Override
    public QueryResults<QuietTeam> page(QuietTeam params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Where.NotNullEq(params.getId(), quietTeam.id, builder);
        Where.NotBlankContains(params.getTeamName(), quietTeam.teamName, builder);
        Where.NotBlankContains(params.getSlogan(), quietTeam.slogan, builder);
        QueryResults<QuietTeam> result = jpaQueryFactory.selectFrom(quietTeam).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
        if (CollectionUtils.isNotEmpty(result.getResults())) {
            Set<Long> teamIds = result.getResults().stream().map(QuietTeam::getId).collect(Collectors.toSet());
            List<QuietTeamUser> allTeamUsers = teamUserService.findAllUsersByTeamIds(teamIds);
            Map<Long, List<QuietTeamUser>> teamIdToTeamUsers = allTeamUsers.stream()
                    .collect(Collectors.groupingBy(QuietTeamUser::getTeamId));
            Set<Long> allUserIds = allTeamUsers.stream().map(QuietTeamUser::getUserId).collect(Collectors.toSet());
            List<QuietTeamUserRole> userTeamRoles = teamUserRoleService
                    .findByTeamUserIds(allTeamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
            Map<Long, List<QuietTeamUserRole>> teamUserIdToRoles = userTeamRoles.stream()
                    .collect(Collectors.groupingBy(QuietTeamUserRole::getTeamUserId));
            Map<Long, QuietUser> userIdToUserInfo = userService.findByUserIds(allUserIds).stream()
                    .collect(Collectors.toMap(QuietUser::getId, u -> u));
            QuietRole productOwner = roleService.findByRoleName(RoleNames.ProductOwner);
            QuietRole scrumMaster = roleService.findByRoleName(RoleNames.ScrumMaster);
            for (QuietTeam quietTeam : result.getResults()) {
                List<QuietTeamUser> quietTeamUsers = teamIdToTeamUsers.get(quietTeam.getId());
                if (CollectionUtils.isNotEmpty(quietTeamUsers)) {
                    List<QuietUser> members = new ArrayList<>();
                    List<QuietUser> teamProductOwners = new ArrayList<>();
                    List<QuietUser> teamScrumMasters = new ArrayList<>();
                    for (QuietTeamUser quietTeamUser : quietTeamUsers) {
                        List<QuietTeamUserRole> quietTeamUserRoles = teamUserIdToRoles.get(quietTeamUser.getId());
                        if (CollectionUtils.isNotEmpty(quietTeamUserRoles)) {
                            for (QuietTeamUserRole quietTeamUserRole : quietTeamUserRoles) {
                                if (quietTeamUserRole.getRoleId().equals(productOwner.getId())) {
                                    teamProductOwners.add(userIdToUserInfo.get(quietTeamUser.getUserId()));
                                }
                                if (quietTeamUserRole.getRoleId().equals(scrumMaster.getId())) {
                                    teamScrumMasters.add(userIdToUserInfo.get(quietTeamUser.getUserId()));
                                }
                            }
                        } else {
                            members.add(userIdToUserInfo.get(quietTeamUser.getUserId()));
                        }
                    }
                    quietTeam.setMembers(members);
                    quietTeam.setProductOwners(teamProductOwners);
                    quietTeam.setScrumMasters(teamScrumMasters);
                }
                
            }
        }
        return result;
    }
    
    @Override
    public QuietTeam saveOrUpdate(QuietTeam team) {
        QuietTeam exist = teamRepository.getByTeamName(team.getTeamName());
        if (exist != null && !exist.getId().equals(team.getId())) {
            throw new ServiceException("team.teamName.exist", team.getTeamName());
        }
        // 删除所有旧数据，包括团队成员信息、团队成员的角色信息
        teamUserService.deleteByTeamId(team.getId());
        Set<Long> memberIds = new HashSet<>();
        // 保存成员信息，包括 PO 和 SM
        this.addMemberId(memberIds, team.getMembers());
        this.addMemberId(memberIds, team.getProductOwners());
        this.addMemberId(memberIds, team.getScrumMasters());
        List<QuietTeamUser> quietTeamUsers = new ArrayList<>(memberIds.size());
        for (Long memberId : memberIds) {
            quietTeamUsers.add(new QuietTeamUser(team.getId(), memberId));
        }
        // 添加团队成员信息
        teamUserService.saveAllWithoutCheck(quietTeamUsers);
        // 添加 PO 角色
        if (CollectionUtils.isNotEmpty(team.getProductOwners())) {
            teamUserRoleService.addRoleForTeamWithoutCheck(team.getId(),
                    team.getProductOwners().stream().map(QuietUser::getId).collect(Collectors.toSet()),
                    RoleNames.ProductOwner);
        }
        // 添加 SM 角色
        if (CollectionUtils.isNotEmpty(team.getScrumMasters())) {
            teamUserRoleService.addRoleForTeamWithoutCheck(team.getId(),
                    team.getScrumMasters().stream().map(QuietUser::getId).collect(Collectors.toSet()),
                    RoleNames.ScrumMaster);
        }
        // 更新团队信息
        return teamRepository.saveAndFlush(team);
    }
    
    private void addMemberId(Set<Long> memberIds, List<QuietUser> members) {
        if (CollectionUtils.isNotEmpty(members)) {
            for (QuietUser member : members) {
                memberIds.add(member.getId());
            }
        }
    }
    
    @Override
    public void deleteTeam(Long deleteId) {
        // TODO 删除团队中的成员信息
        // 删除团队成员信息
        teamUserService.deleteByTeamId(deleteId);
        // 删除团队信息
        teamRepository.deleteById(deleteId);
    }
}
