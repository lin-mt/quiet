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

package com.gitee.quiet.service.vo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.gitee.quiet.service.json.filter.JsonFilterName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * VO 基本信息
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@JsonFilter(JsonFilterName.HAS_ROLE)
public class BaseVO {

    /**
     * ID
     */
    private Long id;

    /**
     * 创建者ID
     */
    private Long creator;

    /**
     * 更新者ID
     */
    private Long updater;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtUpdate;
}
