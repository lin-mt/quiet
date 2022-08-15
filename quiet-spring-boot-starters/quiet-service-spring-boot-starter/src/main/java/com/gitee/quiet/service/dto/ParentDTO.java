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

package com.gitee.quiet.service.dto;

import com.gitee.quiet.common.core.entity.Parent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.List;

/**
 * 带有父级ID的DTO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ParentDTO<T extends ParentDTO<T>> extends BaseDTO implements Parent<T> {

  /** 父级ID */
  private Long parentId;

  @Transient private List<T> children;
}
