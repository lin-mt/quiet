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

import com.gitee.quiet.jpa.entity.SortableEntity;
import com.gitee.quiet.scrum.enums.BuildTool;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_project")
public class ScrumProject extends SortableEntity {

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
  @Column(name = "remark", length = 100)
  private String remark;

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

  /** 项目组ID */
  @Column(name = "group_id")
  private Long groupId;

  /** 构建工具 */
  @Column(name = "build_tool", length = 6)
  private BuildTool buildTool;

  /** 项目开始时间 */
  @Column(name = "start_time")
  private LocalDateTime startTime;

  /** 项目结束时间 */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /** 负责项目的团队ID */
  @NotNull
  @Column(name = "team_id")
  private Long teamId;
}
