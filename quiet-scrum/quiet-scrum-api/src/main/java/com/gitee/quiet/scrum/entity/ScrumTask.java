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

import com.gitee.quiet.jpa.entity.Dict;
import com.gitee.quiet.jpa.entity.SortableEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 任务信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_task")
public class ScrumTask extends SortableEntity {

  /** 任务标题 */
  @NotBlank
  @Length(max = 10)
  @Column(name = "title", nullable = false, length = 10)
  private String title;

  /** 任务类型 */
  @NotNull
  @Column(name = "task_type", nullable = false, length = 30)
  private Dict type;

  /** 所属需求ID */
  @NotNull
  @Column(name = "demand_id", nullable = false)
  private Long demandId;

  /** 任务的当前步骤ID */
  @NotNull
  @Column(name = "task_step_id", nullable = false)
  private Long taskStepId;

  /** 执行者 */
  @NotNull
  @Column(name = "executor_id", nullable = false)
  private Long executorId;

  /** 参与者（最多20人参与） */
  @Size(max = 20)
  @Column(name = "participant", length = 380)
  private Set<Long> participant;

  /** 前置任务 */
  @Size(max = 20)
  @Column(name = "pre_task", length = 380)
  private Set<Long> preTaskIds;

  /** 任务开始时间 */
  @Column(name = "start_time")
  private LocalDateTime startTime;

  /** 任务结束时间 */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /** 任务备注信息 */
  @Length(max = 3000)
  @Column(name = "remark", length = 3000)
  private String remark;
}
