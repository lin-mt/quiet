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

import com.gitee.quiet.common.core.entity.Serial;
import com.gitee.quiet.jpa.entity.SerialEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 迭代信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_iteration")
public class ScrumIteration extends SerialEntity {

  /** 迭代名称 */
  @NotBlank
  @Length(max = 30)
  @Column(name = "iteration_name", nullable = false, length = 30)
  private String name;

  /** 所属版本ID */
  @NotNull
  @Column(name = "version_id", nullable = false)
  private Long versionId;

  /** 迭代计划开始日期 */
  @NotNull
  @Column(name = "plan_start_date", nullable = false)
  private LocalDate planStartDate;

  /** 迭代计划结束日期 */
  @NotNull
  @Column(name = "plan_end_date", nullable = false)
  private LocalDate planEndDate;

  /** 迭代开始时间 */
  @Column(name = "start_time")
  private LocalDateTime startTime;

  /** 迭代结束时间 */
  @Column(name = "end_time")
  private LocalDateTime endTime;

  /** 备注信息 */
  @Length(max = 1000)
  @Column(name = "remark", nullable = false, length = 1000)
  private String remark;

  @Override
  public int compareTo(@Nullable Serial other) {
    int compare = super.compareTo(other);
    if (compare == 0 && other instanceof ScrumIteration) {
      ScrumIteration otherIteration = (ScrumIteration) other;
      compare = planStartDate.compareTo(otherIteration.getPlanStartDate());
      if (compare == 0) {
        compare = getGmtCreate().compareTo(otherIteration.getGmtCreate());
      }
    }
    return compare;
  }
}
