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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.enums.BuildTool;
import com.gitee.quiet.service.vo.SortableVO;
import com.gitee.quiet.system.entity.QuietTeam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumProjectVO extends SortableVO {

  /** 项目名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 项目经理 */
  @NotNull private Long manager;

  /** 项目描述信息 */
  @Length(max = 100)
  private String description;

  /** 需求前缀 */
  @Length(max = 6)
  private String demandPrefix;

  /** 任务前缀 */
  @Length(max = 6)
  private String taskPrefix;

  /** 模板ID */
  @NotNull private Long templateId;

  /** 项目组ID */
  private Long groupId;

  /** 构建工具 */
  private BuildTool buildTool;

  /** 项目开始时间 */
  private LocalDateTime startTime;

  /** 项目结束时间 */
  private LocalDateTime endTime;

  /** 负责的团队ID集合 */
  private Set<Long> teamIds;

  /** 项目经理用户名 */
  private String managerName;

  /** 模板名称 */
  private String templateName;

  /** 负责该项目的团队信息 */
  private List<QuietTeam> teams;
}
