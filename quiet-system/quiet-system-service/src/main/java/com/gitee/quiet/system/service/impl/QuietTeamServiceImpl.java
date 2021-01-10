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
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.repository.QuietTeamRepository;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserRoleService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    
    public QuietTeamServiceImpl(JPAQueryFactory jpaQueryFactory, QuietTeamRepository teamRepository,
            QuietTeamUserService teamUserService, QuietTeamUserRoleService teamUserRoleService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.teamRepository = teamRepository;
        this.teamUserService = teamUserService;
        this.teamUserRoleService = teamUserRoleService;
    }
    
    @Override
    public QueryResults<QuietTeam> page(QuietTeam params, Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        Where.NotNullEq(params.getId(), quietTeam.id, builder);
        Where.NotBlankContains(params.getTeamName(), quietTeam.teamName, builder);
        Where.NotBlankContains(params.getSlogan(), quietTeam.slogan, builder);
        return jpaQueryFactory.selectFrom(quietTeam).where(builder).offset(page.getOffset()).limit(page.getPageSize())
                .fetchResults();
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
