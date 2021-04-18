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
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumProjectTeam;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumProjectRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumProjectTeamService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import com.gitee.quiet.scrum.vo.MyScrumProject;
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
    
    private final ScrumVersionService versionService;
    
    private final ScrumDemandService demandService;
    
    private final ScrumTemplateService templateService;
    
    @DubboReference
    private QuietTeamUserService quietTeamUserService;
    
    @DubboReference
    private QuietUserService quietUserService;
    
    @DubboReference
    private QuietTeamService quietTeamService;
    
    public ScrumProjectServiceImpl(ScrumProjectRepository projectRepository,
            @Lazy ScrumProjectTeamService projectTeamService, ScrumVersionService versionService,
            ScrumDemandService demandService, @Lazy ScrumTemplateService templateService) {
        this.projectRepository = projectRepository;
        this.projectTeamService = projectTeamService;
        this.versionService = versionService;
        this.demandService = demandService;
        this.templateService = templateService;
    }
    
    @Override
    public MyScrumProject allProjectByUserId(@NotNull Long userId) {
        MyScrumProject myScrumProject = new MyScrumProject();
        Set<Long> allProjectIds = new HashSet<>();
        Set<Long> allTemplateIds = new HashSet<>();
        List<ScrumProject> projectManaged = projectRepository.findAllByManager(userId);
        projectManaged.forEach(p -> {
            allProjectIds.add(p.getId());
            allTemplateIds.add(p.getTemplateId());
        });
        myScrumProject.setProjectManaged(projectManaged);
        List<QuietTeamUser> teamUsers = quietTeamUserService.findAllByUserId(userId);
        if (CollectionUtils.isNotEmpty(teamUsers)) {
            Set<Long> teamIds = teamUsers.stream().map(QuietTeamUser::getTeamId).collect(Collectors.toSet());
            List<ScrumProject> projectInvolved = projectTeamService.findAllProjectsByTeamIds(teamIds);
            if (CollectionUtils.isNotEmpty(projectManaged)) {
                projectManaged.forEach(project -> project.setManagerName(SpringSecurityUtils.getCurrentUserFullName()));
                Set<Long> manageProjectIds = projectManaged.stream().map(ScrumProject::getId)
                        .collect(Collectors.toSet());
                projectInvolved = projectInvolved.stream()
                        .filter(project -> !manageProjectIds.contains(project.getId())).collect(Collectors.toList());
            }
            Set<Long> managerIds = projectInvolved.stream().map(ScrumProject::getManager).collect(Collectors.toSet());
            Map<Long, String> userIdToFullName = quietUserService.findByUserIds(managerIds).stream()
                    .collect(Collectors.toMap(QuietUser::getId, QuietUser::getFullName));
            projectInvolved.forEach(project -> {
                project.setManagerName(userIdToFullName.get(project.getManager()));
                allProjectIds.add(project.getId());
                allTemplateIds.add(project.getTemplateId());
            });
            myScrumProject.setProjectInvolved(projectInvolved);
        }
        List<ScrumProjectTeam> allProjectTeams = projectTeamService.findAllByProjectIds(allProjectIds);
        Set<Long> allTeamIds = allProjectTeams.stream().map(ScrumProjectTeam::getTeamId).collect(Collectors.toSet());
        Map<Long, List<ScrumProjectTeam>> projectIdToTeams = allProjectTeams.stream()
                .collect(Collectors.groupingBy(ScrumProjectTeam::getProjectId));
        Map<Long, QuietTeam> teamIdToTeamInfos = quietTeamService.findAllByIds(allTeamIds).stream()
                .collect(Collectors.toMap(QuietTeam::getId, q -> q));
        Map<Long, ScrumTemplate> templateIdToInfos = templateService.findAllByIds(allTemplateIds).stream()
                .collect(Collectors.toMap(ScrumTemplate::getId, s -> s));
        addExpandInfo(projectManaged, projectIdToTeams, teamIdToTeamInfos, templateIdToInfos);
        addExpandInfo(myScrumProject.getProjectInvolved(), projectIdToTeams, teamIdToTeamInfos, templateIdToInfos);
        return myScrumProject;
    }
    
    private void addExpandInfo(List<ScrumProject> projects, Map<Long, List<ScrumProjectTeam>> projectIdToTeams,
            Map<Long, QuietTeam> teamIdToTeamInfos, Map<Long, ScrumTemplate> templateIdToInfos) {
        if (CollectionUtils.isNotEmpty(projects)) {
            projects.forEach(project -> {
                project.setTemplateName(templateIdToInfos.get(project.getTemplateId()).getName());
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
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        // 删除版本信息
        versionService.deleteAllByProjectId(id);
        // 删除需求信息
        demandService.deleteAllByProjectId(id);
        // 删除项目团队信息
        projectTeamService.deleteAllByProjectId(id);
        // 删除项目信息
        projectRepository.deleteById(id);
    }
    
    @Override
    public long countByTemplateId(Long templateId) {
        return projectRepository.countByTemplateId(templateId);
    }
    
    private void checkProjectInfo(ScrumProject project) {
        ScrumProject exist = projectRepository.findByNameAndManager(project.getName(), project.getManager());
        if (exist != null && !exist.getId().equals(project.getId())) {
            throw new ServiceException("project.manager.projectName.exist", project.getName());
        }
        List<ScrumProject> projects = projectTeamService.findAllProjectsByTeamIds(project.getTeamIds());
        if (CollectionUtils.isNotEmpty(projects)) {
            List<ScrumProject> existProjectName = projects.stream().filter(p -> p.getName().equals(project.getName()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(existProjectName)) {
                throw new ServiceException("project.team.projectName.exist", project.getName());
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
