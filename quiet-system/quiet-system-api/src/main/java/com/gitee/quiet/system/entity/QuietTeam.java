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
import org.hibernate.validator.constraints.Length;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 团队.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_team")
public class QuietTeam extends BaseEntity {
    
    /**
     * 团队名称
     */
    @NotEmpty(message = "{team.teamName}{not.empty}")
    @Length(max = 16, message = "{team.teamName.length}{length.max.limit}")
    private String teamName;
    
    /**
     * 标语
     */
    @Length(max = 30, message = "{team.slogan.length}{length.max.limit}")
    private String slogan;
    
    /**
     * 团队角色与成员信息信息
     */
    @Transient
    private Map<Long, List<QuietUser>> roleIdToUsers;
    
    /**
     * 团队PO
     */
    @Transient
    private List<QuietUser> productOwners;
    
    /**
     * 团队SM
     */
    @Transient
    private List<QuietUser> scrumMasters;
    
    /**
     * 团队成员
     */
    @Transient
    private List<QuietUser> members;
    
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
    
    public Map<Long, List<QuietUser>> getRoleIdToUsers() {
        return roleIdToUsers;
    }
    
    public void setRoleIdToUsers(Map<Long, List<QuietUser>> roleIdToUsers) {
        this.roleIdToUsers = roleIdToUsers;
    }
    
    public List<QuietUser> getProductOwners() {
        return productOwners;
    }
    
    public void setProductOwners(List<QuietUser> productOwners) {
        this.productOwners = productOwners;
    }
    
    public List<QuietUser> getScrumMasters() {
        return scrumMasters;
    }
    
    public void setScrumMasters(List<QuietUser> scrumMasters) {
        this.scrumMasters = scrumMasters;
    }
    
    public List<QuietUser> getMembers() {
        return members;
    }
    
    public void setMembers(List<QuietUser> members) {
        this.members = members;
    }
}
