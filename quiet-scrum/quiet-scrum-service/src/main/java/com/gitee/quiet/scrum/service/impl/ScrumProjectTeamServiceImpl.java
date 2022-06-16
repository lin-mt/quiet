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

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;
import com.gitee.quiet.scrum.repository.ScrumProjectTeamRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumProjectTeamService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目团队信息的service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumProjectTeamServiceImpl implements ScrumProjectTeamService {

    private final ScrumProjectTeamRepository projectTeamRepository;

    private final ScrumProjectService projectService;

    public ScrumProjectTeamServiceImpl(ScrumProjectTeamRepository projectTeamRepository,
        ScrumProjectService projectService) {
        this.projectTeamRepository = projectTeamRepository;
        this.projectService = projectService;
    }

    @Override
    public List<ScrumProject> findAllProjectsByTeamIds(Set<Long> teamIds) {
        List<ScrumProjectTeam> projectTeams = projectTeamRepository.findAllByTeamIdIn(teamIds);
        if (CollectionUtils.isNotEmpty(projectTeams)) {
            Set<Long> projectIds = projectTeams.stream().map(ScrumProjectTeam::getProjectId)
                .collect(Collectors.toSet());
            return projectService.findAllByIds(projectIds);
        }
        return List.of();
    }

    @Override
    public List<ScrumProjectTeam> saveAll(List<ScrumProjectTeam> projectTeams) {
        return projectTeamRepository.saveAll(projectTeams);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByProjectId(Long projectId) {
        projectTeamRepository.deleteAllByProjectId(projectId);
    }

    @Override
    public List<ScrumProjectTeam> findAllByProjectIds(Set<Long> projectIds) {
        return projectTeamRepository.findAllByProjectIdIn(projectIds);
    }
}
