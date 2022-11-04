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

import com.gitee.quiet.jpa.entity.Dict;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.repository.ScrumIterationRepository;
import com.gitee.quiet.scrum.repository.ScrumPriorityRepository;
import com.gitee.quiet.scrum.repository.ScrumProjectRepository;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.dto.ParentAndSortableDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 需求信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumDemandDTO extends ParentAndSortableDTO<ScrumDemandDTO> {

  /** 需求标题 */
  @NotBlank
  @Length(max = 30)
  private String title;

  /** 需求类型 */
  @NotNull private Dict type;

  /** 项目ID */
  @NotNull
  @ExistId(
      repository = ScrumProjectRepository.class,
      message = "{quiet.validation.project.id.notExist}")
  private Long projectId;

  /** 该需求所优化的需求ID，A需求优化了B需求，则A需求的optimizeDemandId为B需求的ID */
  @ExistId(
      repository = ScrumDemandRepository.class,
      message = "{quiet.validation.optimizeDemandId.notExist}")
  private Long optimizeDemandId;

  /** 所属迭代ID */
  @ExistId(
      repository = ScrumIterationRepository.class,
      message = "{quiet.validation.iteration.id.notExist}")
  private Long iterationId;

  /** 优先级ID */
  @NotNull
  @ExistId(
      repository = ScrumPriorityRepository.class,
      message = "{quiet.validation.priority.id.notExist}")
  private Long priorityId;

  /** 需求开始时间 */
  private LocalDateTime startTime;

  /** 需求结束时间 */
  private LocalDateTime endTime;

  /** 备注信息 */
  @Length(max = 3000)
  private String remark;

  /** 是否已规划 */
  private Boolean planned;
}
