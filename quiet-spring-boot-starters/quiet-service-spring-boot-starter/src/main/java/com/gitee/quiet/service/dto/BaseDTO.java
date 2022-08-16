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

import com.gitee.quiet.validation.groups.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Data
public class BaseDTO {

  private static final String ASCEND = "ascend";

  private static final String DESCEND = "descend";

  /** ID */
  @Null(groups = Create.class)
  @NotNull(groups = {IdValid.class, Update.class})
  private Long id;

  /** 查询关键词 */
  private String keyword;

  /** 第几页 */
  @NotNull(groups = PageValid.class)
  @Min(value = 1, groups = PageValid.class)
  private Integer current;

  /** 分页大小 */
  @NotNull(groups = PageValid.class)
  @Min(value = 1, groups = PageValid.class)
  private Integer pageSize;

  /** 跳过几条数据 */
  @Min(value = 0L, groups = OffsetLimitValid.class)
  @NotNull(groups = OffsetLimitValid.class)
  private Long offset;

  /** 查询几条数据 */
  @Range(max = 300L, groups = OffsetLimitValid.class)
  @NotNull(groups = OffsetLimitValid.class)
  private Long limit;

  private Long creator;

  private Long updater;

  private LocalDateTime gmtCreate;

  private LocalDateTime gmtUpdate;

  public Pageable page() {
    return PageRequest.of(getCurrent() - 1, this.getPageSize());
  }
}
