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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 团队成员信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_team_user")
public class QuietTeamUser extends BaseEntity {
    
    /**
     * 团队ID
     */
    @Column(name = "team_id", nullable = false)
    @NotNull(message = "{teamUser.teamId}{not.null}")
    private Long teamId;
    
    /**
     * 成员ID
     */
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "{teamUser.userId}{not.null}")
    private Long userId;
    
    public Long getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public QuietTeamUser() {
    }
    
    public QuietTeamUser(@NotNull(message = "{teamUser.teamId}{not.null}") Long teamId,
            @NotNull(message = "{teamUser.userId}{not.null}") Long userId) {
        this.teamId = teamId;
        this.userId = userId;
    }
}
