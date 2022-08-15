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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.jpa.entity.SerialEntity;
import com.gitee.quiet.scrum.enums.BuildTool;
import com.gitee.quiet.system.entity.QuietTeam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_project")
public class ScrumProject extends SerialEntity {

  /** 项目名称 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "project_name", nullable = false, length = 30)
  private String name;

  /** 项目经理 */
  @NotNull
  @Column(name = "manager", nullable = false)
  private Long manager;

  /** 项目描述信息 */
  @Length(max = 100)
  @Column(name = "project_description", length = 100)
  private String description;

  /** 需求前缀 */
  @Length(max = 6)
  @Column(name = "demand_prefix", length = 6)
  private String demandPrefix;

  /** 任务前缀 */
  @Length(max = 6)
  @Column(name = "task_prefix", length = 6)
  private String taskPrefix;

  /** 模板ID */
  @NotNull
  @Column(name = "template_id")
  private Long templateId;

  /** 构建工具 */
  @Column(name = "build_tool", length = 6)
  private BuildTool buildTool;

  /** 项目开始时间 */
  @Column(name = "start_time")
  private LocalDateTime startTime;

  /** 项目结束时间 */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /** 负责的团队ID集合 */
  @Transient private Set<Long> teamIds;

  /** 项目经理用户名 */
  @Transient private String managerName;

  /** 模板名称 */
  @Transient private String templateName;

  /** 负责该项目的团队信息 */
  @Transient private List<QuietTeam> teams;

  public void addTeamInfo(QuietTeam quietTeam) {
    if (getTeams() == null) {
      setTeams(new ArrayList<>());
    }
    getTeams().add(quietTeam);
  }
}
