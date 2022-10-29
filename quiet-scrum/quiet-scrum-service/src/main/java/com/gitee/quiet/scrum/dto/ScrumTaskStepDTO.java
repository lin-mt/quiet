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

import com.gitee.quiet.scrum.repository.ScrumTemplateRepository;
import com.gitee.quiet.service.annotation.ExistId;
import com.gitee.quiet.service.dto.SortableDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务步骤.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumTaskStepDTO extends SortableDTO {

  /** 步骤名称 */
  @NotBlank
  @Length(max = 10)
  private String name;

  /** 所属模板ID */
  @NotNull
  @ExistId(
      message = "quiet.validation.template.id.notExist",
      repository = ScrumTemplateRepository.class)
  private Long templateId;

  /** 步骤备注信息 */
  @Length(max = 30)
  private String remark;
}
