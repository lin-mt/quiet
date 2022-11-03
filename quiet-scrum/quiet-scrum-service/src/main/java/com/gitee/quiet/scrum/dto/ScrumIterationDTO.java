/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.scrum.dto;

import com.gitee.quiet.common.core.entity.Sortable;
import com.gitee.quiet.scrum.repository.ScrumVersionRepository;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.dto.SortableDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
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
public class ScrumIterationDTO extends SortableDTO {

  /** 迭代名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 所属版本ID */
  @NotNull
  @ExistId(
      repository = ScrumVersionRepository.class,
      message = "{quiet.validation.version.id.notExist}")
  private Long versionId;

  /** 迭代计划开始日期 */
  @NotNull private LocalDate planStartDate;

  /** 迭代计划结束日期 */
  @NotNull private LocalDate planEndDate;

  /** 迭代开始时间 */
  private LocalDateTime startTime;

  /** 迭代结束时间 */
  private LocalDateTime endTime;

  /** 备注信息 */
  @Length(max = 1000)
  private String remark;

  @Override
  public int compareTo(@Nullable Sortable other) {
    int compare = super.compareTo(other);
    if (compare == 0 && other instanceof ScrumIterationDTO) {
      ScrumIterationDTO otherIteration = (ScrumIterationDTO) other;
      compare = planStartDate.compareTo(otherIteration.getPlanStartDate());
      if (compare == 0) {
        compare = getGmtCreate().compareTo(otherIteration.getGmtCreate());
      }
    }
    return compare;
  }
}
