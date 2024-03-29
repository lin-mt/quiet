/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.jpa.utils.SelectBuilder;
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

  /** 团队名称 */
  @NotBlank
  @Length(max = 8)
  @Column(name = "team_name", nullable = false, length = 16)
  private String teamName;

  /** 标语 */
  @Column(name = "slogan", length = 60)
  @Length(max = 60)
  private String slogan;

  /** 团队角色与成员信息信息 */
  @Transient private Map<Long, List<QuietUser>> roleIdToUsers;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietTeam.id)
        .notBlankContains(getTeamName(), quietTeam.teamName)
        .notBlankContains(getSlogan(), quietTeam.slogan)
        .getPredicate();
  }
}
