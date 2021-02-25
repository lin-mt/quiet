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

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.util.SpringSecurityUtils;
import com.gitee.quiet.scrum.MyScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;
import com.gitee.quiet.scrum.repository.ScrumProjectRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumProjectTeamService;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumProjectServiceImpl implements ScrumProjectService {
    
    private final ScrumProjectRepository projectRepository;
    
    private final ScrumProjectTeamService projectTeamService;
    
    @DubboReference
    private QuietTeamUserService teamUserService;
    
    @DubboReference
    private QuietUserService userService;
    
    public ScrumProjectServiceImpl(ScrumProjectRepository projectRepository,
            @Lazy ScrumProjectTeamService projectTeamService) {
        this.projectRepository = projectRepository;
        this.projectTeamService = projectTeamService;
    }
    
    @Override
    public MyScrumProject allProjectByUserId(Long userId) {
        MyScrumProject myScrumProject = new MyScrumProject();
        List<ScrumProject> managedProjects = projectRepository.findAllByManager(userId);
        myScrumProject.setManagedProjects(managedProjects);
        List<QuietTeamUser> teamUsers = teamUserService.findAllByUserId(userId);
        if (CollectionUtils.isNotEmpty(teamUsers)) {
            Set<Long> teamIds = teamUsers.stream().map(QuietTeamUser::getTeamId).collect(Collectors.toSet());
            List<ScrumProject> projects = projectTeamService.findAllProjectsByTeamIds(teamIds);
            if (CollectionUtils.isNotEmpty(managedProjects)) {
                managedProjects.forEach(project -> project.setManagerName(SpringSecurityUtils.getCurrentUserName()));
                Set<Long> manageProjectIds = managedProjects.stream().map(ScrumProject::getId)
                        .collect(Collectors.toSet());
                projects = projects.stream().filter(project -> !manageProjectIds.contains(project.getId()))
                        .collect(Collectors.toList());
                Set<Long> managerIds = projects.stream().map(ScrumProject::getManager).collect(Collectors.toSet());
                Map<Long, String> userIdToUsername = userService.findByUserIds(managerIds).stream()
                        .collect(Collectors.toMap(QuietUser::getId, QuietUser::getUsername));
                projects.forEach(project -> project.setManagerName(userIdToUsername.get(project.getManager())));
            }
            myScrumProject.setProjects(projects);
        }
        return myScrumProject;
    }
    
    @Override
    public ScrumProject save(ScrumProject save) {
        ScrumProject exist = projectRepository.findByNameAndManager(save.getName(), save.getManager());
        if (exist != null) {
            throw new ServiceException("project.manager.projectName.exist", save.getName());
        }
        List<ScrumProject> projects = projectTeamService.findAllProjectsByTeamIds(save.getTeamIds());
        if (CollectionUtils.isNotEmpty(projects)) {
            List<ScrumProject> duplicateProjectName = projects.stream()
                    .filter(project -> project.getName().equals(save.getName())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(duplicateProjectName)) {
                throw new ServiceException("project.team.projectName.duplicate", save.getName());
            }
        }
        ScrumProject project = projectRepository.save(save);
        List<ScrumProjectTeam> projectTeams = new ArrayList<>(save.getTeamIds().size());
        for (Long teamId : save.getTeamIds()) {
            ScrumProjectTeam projectTeam = new ScrumProjectTeam();
            projectTeam.setTeamId(teamId);
            projectTeam.setProjectId(project.getId());
            projectTeams.add(projectTeam);
        }
        projectTeamService.saveAll(projectTeams);
        return project;
    }
    
    @Override
    public List<ScrumProject> findAllByIds(Set<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return projectRepository.findAllById(ids);
        }
        return List.of();
    }
}
