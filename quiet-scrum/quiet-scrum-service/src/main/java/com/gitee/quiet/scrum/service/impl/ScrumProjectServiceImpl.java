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
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.entity.QuietTeamUser;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.service.QuietTeamUserService;
import com.gitee.quiet.system.service.QuietUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
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
    private QuietTeamUserService quietTeamUserService;
    
    @DubboReference
    private QuietUserService quietUserService;
    
    @DubboReference
    private QuietTeamService quietTeamService;
    
    public ScrumProjectServiceImpl(ScrumProjectRepository projectRepository,
            @Lazy ScrumProjectTeamService projectTeamService) {
        this.projectRepository = projectRepository;
        this.projectTeamService = projectTeamService;
    }
    
    @Override
    public MyScrumProject allProjectByUserId(@NotNull Long userId) {
        MyScrumProject myScrumProject = new MyScrumProject();
        Set<Long> allProjectIds = new HashSet<>();
        List<ScrumProject> managedProjects = projectRepository.findAllByManager(userId);
        managedProjects.forEach(p -> allProjectIds.add(p.getId()));
        myScrumProject.setManagedProjects(managedProjects);
        List<QuietTeamUser> teamUsers = quietTeamUserService.findAllByUserId(userId);
        if (CollectionUtils.isNotEmpty(teamUsers)) {
            Set<Long> teamIds = teamUsers.stream().map(QuietTeamUser::getTeamId).collect(Collectors.toSet());
            List<ScrumProject> projects = projectTeamService.findAllProjectsByTeamIds(teamIds);
            if (CollectionUtils.isNotEmpty(managedProjects)) {
                managedProjects.forEach(project -> project.setManagerName(SpringSecurityUtils.getCurrentUserFullName()));
                Set<Long> manageProjectIds = managedProjects.stream().map(ScrumProject::getId)
                        .collect(Collectors.toSet());
                projects = projects.stream().filter(project -> !manageProjectIds.contains(project.getId()))
                        .collect(Collectors.toList());
            }
            Set<Long> managerIds = projects.stream().map(ScrumProject::getManager).collect(Collectors.toSet());
            Map<Long, String> userIdToFullName = quietUserService.findByUserIds(managerIds).stream()
                    .collect(Collectors.toMap(QuietUser::getId, QuietUser::getFullName));
            projects.forEach(project -> {
                project.setManagerName(userIdToFullName.get(project.getManager()));
                allProjectIds.add(project.getId());
            });
            myScrumProject.setProjects(projects);
        }
        List<ScrumProjectTeam> allProjectTeams = projectTeamService.findAllByProjectIds(allProjectIds);
        Set<Long> allTeamIds = allProjectTeams.stream().map(ScrumProjectTeam::getTeamId).collect(Collectors.toSet());
        Map<Long, List<ScrumProjectTeam>> projectIdToTeams = allProjectTeams.stream()
                .collect(Collectors.groupingBy(ScrumProjectTeam::getProjectId));
        Map<Long, QuietTeam> teamIdToTeamInfos = quietTeamService.findAllByIds(allTeamIds).stream()
                .collect(Collectors.toMap(QuietTeam::getId, q -> q));
        addTeamInfo(managedProjects, projectIdToTeams, teamIdToTeamInfos);
        addTeamInfo(myScrumProject.getProjects(), projectIdToTeams, teamIdToTeamInfos);
        return myScrumProject;
    }
    
    private void addTeamInfo(List<ScrumProject> projects, Map<Long, List<ScrumProjectTeam>> projectIdToTeams,
            Map<Long, QuietTeam> teamIdToTeamInfos) {
        if (CollectionUtils.isNotEmpty(projects)) {
            projects.forEach(project -> {
                List<ScrumProjectTeam> projectTeams = projectIdToTeams.get(project.getId());
                projectTeams.forEach(pt -> project.addTeamInfo(teamIdToTeamInfos.get(pt.getTeamId())));
            });
        }
    }
    
    @Override
    public ScrumProject save(@NotNull ScrumProject save) {
        checkProjectInfo(save);
        ScrumProject project = projectRepository.save(save);
        addProjectTeams(save.getTeamIds(), project);
        return project;
    }
    
    @Override
    public List<ScrumProject> findAllByIds(Set<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            return projectRepository.findAllById(ids);
        }
        return List.of();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScrumProject update(@NotNull ScrumProject update) {
        if (!projectRepository.existsById(update.getId())) {
            throw new ServiceException("project.id.not.exist", update.getId());
        }
        projectTeamService.deleteAllByProjectId(update.getId());
        checkProjectInfo(update);
        ScrumProject project = projectRepository.saveAndFlush(update);
        addProjectTeams(update.getTeamIds(), project);
        return project;
    }
    
    private void checkProjectInfo(ScrumProject project) {
        ScrumProject exist = projectRepository.findByNameAndManager(project.getName(), project.getManager());
        if (exist != null && !exist.getId().equals(project.getId())) {
            throw new ServiceException("project.manager.projectName.exist", project.getName());
        }
        List<ScrumProject> projects = projectTeamService.findAllProjectsByTeamIds(project.getTeamIds());
        if (CollectionUtils.isNotEmpty(projects)) {
            List<ScrumProject> duplicateProjectName = projects.stream()
                    .filter(p -> p.getName().equals(project.getName())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(duplicateProjectName)) {
                throw new ServiceException("project.team.projectName.duplicate", project.getName());
            }
        }
    }
    
    private void addProjectTeams(@NotNull @NotEmpty Set<Long> teamIds, ScrumProject project) {
        List<ScrumProjectTeam> projectTeams = new ArrayList<>(teamIds.size());
        for (Long teamId : teamIds) {
            ScrumProjectTeam projectTeam = new ScrumProjectTeam();
            projectTeam.setTeamId(teamId);
            projectTeam.setProjectId(project.getId());
            projectTeams.add(projectTeam);
        }
        projectTeamService.saveAll(projectTeams);
    }
}
