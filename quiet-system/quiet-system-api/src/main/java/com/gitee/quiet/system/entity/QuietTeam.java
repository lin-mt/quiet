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

import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static com.gitee.quiet.system.entity.QQuietTeam.quietTeam;

/**
 * 团队.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_team")
public class QuietTeam extends BaseEntity {
    
    /**
     * 团队名称
     */
    @NotBlank
    @Length(max = 16)
    @Column(name = "team_name", nullable = false, length = 16)
    private String teamName;
    
    /**
     * 标语
     */
    @Column(name = "slogan", length = 30)
    @Length(max = 30)
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
    
    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), quietTeam.id)
                .notBlankContains(getTeamName(), quietTeam.teamName)
                .notBlankContains(getSlogan(), quietTeam.slogan)
                .getPredicate();
        // @formatter:on
    }
}
