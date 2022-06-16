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

package com.gitee.quiet.scrum.dto;

import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.scrum.dictionary.DemandType;
import com.gitee.quiet.scrum.filter.ScrumDemandFilter;
import com.gitee.quiet.service.dto.ParentAndSerialDTO;
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
public class ScrumDemandDTO extends ParentAndSerialDTO<ScrumDemandDTO> {

    /**
     * 需求标题
     */
    @NotBlank
    @Length(max = 30)
    private String title;

    /**
     * 需求类型
     */
    @NotNull
    private Dictionary<DemandType> type;

    /**
     * 项目ID
     */
    @NotNull
    private Long projectId;

    /**
     * 该需求所优化的需求ID，A需求优化了B需求，则A需求的optimizeDemandId为B需求的ID
     */
    private Long optimizeDemandId;

    /**
     * 所属迭代ID
     */
    private Long iterationId;

    /**
     * 优先级ID
     */
    @NotNull
    private Long priorityId;

    /**
     * 需求开始时间
     */
    private LocalDateTime startTime;

    /**
     * 需求结束时间
     */
    private LocalDateTime endTime;

    /**
     * 备注信息
     */
    @Length(max = 3000)
    private String remark;

    /**
     * 需求过滤条件
     */
    private ScrumDemandFilter demandFilter;
}
