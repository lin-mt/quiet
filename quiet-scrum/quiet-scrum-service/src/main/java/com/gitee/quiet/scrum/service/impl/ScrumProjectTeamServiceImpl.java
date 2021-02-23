/*
 * Copyright 2021 lin-mt@outlook.com
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

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;
import com.gitee.quiet.scrum.repository.ScrumProjectTeamRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumProjectTeamService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

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
}
