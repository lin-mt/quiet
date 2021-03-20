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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.common.service.jpa.entity.SerialEntity;
import com.gitee.quiet.common.service.enums.BuildTool;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.system.entity.QuietTeam;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_project")
public class ScrumProject extends SerialEntity {
    
    /**
     * 项目名称
     */
    @NotNull(message = "{project.name}{not.null}")
    @Length(max = 30, message = "{project.name.length}{length.max.limit}")
    @Column(name = "project_name", nullable = false, length = 30)
    private String name;
    
    /**
     * 项目经理
     */
    @NotNull(message = "{project.manager}{not.null}")
    @Column(name = "manager", nullable = false)
    private Long manager;
    
    /**
     * 项目描述信息
     */
    @Length(max = 100, message = "{project.description.length}{length.max.limit}")
    @Column(name = "project_description", length = 100)
    private String description;
    
    /**
     * 需求前缀
     */
    @Length(max = 6, message = "{project.demandPrefix.length}{length.max.limit}")
    @Column(name = "demand_prefix", length = 6)
    private String demandPrefix;
    
    /**
     * 任务前缀
     */
    @Length(max = 6, message = "{project.taskPrefix.length}{length.max.limit}")
    @Column(name = "task_prefix", length = 6)
    private String taskPrefix;
    
    /**
     * 模板ID
     */
    @Column(name = "template_id")
    private Long templateId;
    
    /**
     * 构建工具
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "build_tool", length = 6)
    private BuildTool buildTool;
    
    /**
     * 负责的团队ID集合
     */
    @Transient
    @NotEmpty(groups = {Create.class, Update.class}, message = "{project.teamIds}{not.empty}")
    private Set<Long> teamIds;
    
    /**
     * 项目经理用户名
     */
    @Transient
    private String managerName;
    
    /**
     * 负责该项目的团队信息
     */
    @Transient
    private List<QuietTeam> teams;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getManager() {
        return manager;
    }
    
    public void setManager(Long manager) {
        this.manager = manager;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDemandPrefix() {
        return demandPrefix;
    }
    
    public void setDemandPrefix(String demandPrefix) {
        this.demandPrefix = demandPrefix;
    }
    
    public String getTaskPrefix() {
        return taskPrefix;
    }
    
    public void setTaskPrefix(String taskPrefix) {
        this.taskPrefix = taskPrefix;
    }
    
    public Long getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    
    public BuildTool getBuildTool() {
        return buildTool;
    }
    
    public void setBuildTool(BuildTool buildTool) {
        this.buildTool = buildTool;
    }
    
    public Set<Long> getTeamIds() {
        return teamIds;
    }
    
    public void setTeamIds(Set<Long> teamIds) {
        this.teamIds = teamIds;
    }
    
    public String getManagerName() {
        return managerName;
    }
    
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    
    public List<QuietTeam> getTeams() {
        return teams;
    }
    
    public void setTeams(List<QuietTeam> teams) {
        this.teams = teams;
    }
    
    public void addTeamInfo(QuietTeam quietTeam) {
        if (getTeams() == null) {
            setTeams(new ArrayList<>());
        }
        getTeams().add(quietTeam);
    }
}
