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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 用户所在团队的角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Table
@Entity(name = "quiet_team_user_role")
public class QuietTeamUserRole extends BaseEntity {
    
    /**
     * 团队-用户ID
     */
    @Column(name = "team_user_id", nullable = false)
    @NotNull(message = "{userTeamRole.teamUserId}{not.null}")
    private Long teamUserId;
    
    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    @NotNull(message = "{userTeamRole.roleId}{not.null}")
    private Long roleId;
    
    public QuietTeamUserRole() {
    }
    
    public QuietTeamUserRole(@NotNull(message = "{userTeamRole.teamUserId}{not.null}") Long teamUserId,
            @NotNull(message = "{userTeamRole.roleId}{not.null}") Long roleId) {
        this.teamUserId = teamUserId;
        this.roleId = roleId;
    }
    
    public Long getTeamUserId() {
        return teamUserId;
    }
    
    public void setTeamUserId(Long teamUserId) {
        this.teamUserId = teamUserId;
    }
    
    public Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
