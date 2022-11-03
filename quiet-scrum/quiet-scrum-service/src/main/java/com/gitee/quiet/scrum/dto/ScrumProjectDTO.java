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

import com.gitee.quiet.scrum.enums.BuildTool;
import com.gitee.quiet.scrum.repository.ScrumProjectGroupRepository;
import com.gitee.quiet.scrum.repository.ScrumTemplateRepository;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.dto.SortableDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
public class ScrumProjectDTO extends SortableDTO {

  /** 项目名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 项目经理 */
  @NotNull private Long manager;

  /** 项目描述信息 */
  @Length(max = 100)
  private String remark;

  /** 需求前缀 */
  @Length(max = 6)
  private String demandPrefix;

  /** 任务前缀 */
  @Length(max = 6)
  private String taskPrefix;

  /** 模板ID */
  @NotNull
  @ExistId(
      repository = ScrumTemplateRepository.class,
      message = "{quiet.validation.template.id.notExist}")
  private Long templateId;

  /** 项目组ID */
  @ExistId(
      repository = ScrumProjectGroupRepository.class,
      message = "{quiet.validation.project-group.id.notExist}")
  private Long groupId;

  /** 负责项目的团队ID */
  // TODO 校验团队ID的合法性
  @NotNull private Long teamId;

  /** 构建工具 */
  private BuildTool buildTool;

  /** 项目开始时间 */
  private LocalDateTime startTime;

  /** 项目结束时间 */
  private LocalDateTime endTime;
}
