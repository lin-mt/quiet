/*
 * Copyright 2021. lin-mt@outlook.com
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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.system.entity.QuietTeam;

import java.util.List;

/**
 * 项目详细信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ScrumProjectDetail {
    
    private ScrumProject project;
    
    private List<QuietTeam> teams;
    
    private List<ScrumVersion> versions;
    
    public ScrumProject getProject() {
        return project;
    }
    
    public void setProject(ScrumProject project) {
        this.project = project;
    }
    
    public List<QuietTeam> getTeams() {
        return teams;
    }
    
    public void setTeams(List<QuietTeam> teams) {
        this.teams = teams;
    }
    
    public List<ScrumVersion> getVersions() {
        return versions;
    }
    
    public void setVersions(List<ScrumVersion> versions) {
        this.versions = versions;
    }
}
