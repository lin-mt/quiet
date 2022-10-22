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

package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.BaseVO;
import com.gitee.quiet.system.entity.QuietUser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 团队VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietTeamVO extends BaseVO {

  /** 团队名称 */
  @NotBlank
  @Length(max = 8)
  private String teamName;

  /** 标语 */
  @Length(max = 60)
  private String slogan;

  /** 团队PO */
  private List<QuietUser> productOwners;

  /** 团队SM */
  private List<QuietUser> scrumMasters;

  /** 团队成员 */
  private List<QuietUser> members;
}
