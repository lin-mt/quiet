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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.base.BaseEntity;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 * 团队.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_team")
public class QuietTeam extends BaseEntity {
    
    /**
     * 团队名称
     */
    @NotEmpty(groups = {Create.class, Update.class}, message = "{team.teamName}{not.empty}")
    @Length(max = 16, message = "{team.teamName.length}{length.max.limit}")
    private String teamName;
    
    /**
     * 标语
     */
    @Length(max = 30, message = "{team.slogan.length}{length.max.limit}")
    private String slogan;
    
    @Basic
    @Column(name = "team_name")
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    @Basic
    @Column(name = "slogan")
    public String getSlogan() {
        return slogan;
    }
    
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
