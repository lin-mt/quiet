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

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.entity.ParentAndSerialEntity;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.scrum.dictionary.DemandType;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.gitee.quiet.scrum.entity.QScrumDemand.scrumDemand;

/**
 * 需求信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_demand")
public class ScrumDemand extends ParentAndSerialEntity<ScrumDemand> {

  /** 需求标题 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "title", nullable = false, length = 30)
  private String title;

  /** 需求类型 */
  @NotNull
  @Column(name = "demand_type", nullable = false, length = 30)
  private Dictionary<DemandType> type;

  /** 项目ID */
  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  /** 该需求所优化的需求ID，A需求优化了B需求，则A需求的optimizeDemandId为B需求的ID */
  @Column(name = "optimize_demand_id")
  private Long optimizeDemandId;

  /** 所属迭代ID */
  @Column(name = "iteration_id")
  private Long iterationId;

  /** 优先级ID */
  @NotNull
  @Column(name = "priority_id", nullable = false)
  private Long priorityId;

  /** 需求开始时间 */
  @Column(name = "start_time")
  private LocalDateTime startTime;

  /** 需求结束时间 */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /** 备注信息 */
  @Length(max = 3000)
  @Column(name = "remark", length = 3000)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    // @formatter:off
    return SelectBuilder.booleanBuilder()
        .notNullEq(getId(), scrumDemand.id)
        .notNullEq(getType(), scrumDemand.type)
        .notBlankContains(getTitle(), scrumDemand.title)
        .notNullEq(getProjectId(), scrumDemand.projectId)
        .notNullEq(getOptimizeDemandId(), scrumDemand.optimizeDemandId)
        .notNullEq(getIterationId(), scrumDemand.iterationId)
        .notNullEq(getPriorityId(), scrumDemand.priorityId)
        .getPredicate();
    // @formatter:on
  }
}
