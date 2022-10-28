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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.jpa.entity.Dict;
import com.gitee.quiet.service.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 路由信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietRouteDTO extends BaseDTO {

  /** 网关的路由ID */
  @NotBlank
  @Length(max = 60)
  private String routeId;

  /** 环境，用于批量修改发布 */
  @NotNull private Dict dictEnv;

  /** 路由目标 */
  @NotBlank
  @Length(max = 200)
  private String uri;

  /** 排序 */
  private int order;

  /** 匹配规则 */
  private Set<String> predicates;

  /** 过滤器 */
  private Set<String> filters;

  /** 备注 */
  @Length(max = 300)
  private String remark;

  /** 匹配规则 */
  private String routePredicate;

  /** 路由过滤器 */
  private String routeFilter;
}
