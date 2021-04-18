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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.entity.ScrumProject;

import java.util.List;

/**
 * 用户参与的所有项目信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class MyScrumProject {
    
    /**
     * 作为项目经理负责的项目
     */
    private List<ScrumProject> projectManaged;
    
    /**
     * 参与的项目（非项目经理）
     */
    private List<ScrumProject> projectInvolved;
    
    public List<ScrumProject> getProjectManaged() {
        return projectManaged;
    }
    
    public void setProjectManaged(List<ScrumProject> projectManaged) {
        this.projectManaged = projectManaged;
    }
    
    public List<ScrumProject> getProjectInvolved() {
        return projectInvolved;
    }
    
    public void setProjectInvolved(List<ScrumProject> projectInvolved) {
        this.projectInvolved = projectInvolved;
    }
}
