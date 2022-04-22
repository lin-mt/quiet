/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.scrum.dto;

import com.gitee.quiet.common.core.entity.Serial;
import com.gitee.quiet.service.dto.ParentAndSerialDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目的版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumVersionDTO extends ParentAndSerialDTO<ScrumVersionDTO> {

    /**
     * 版本名称
     */
    @NotBlank
    @Length(max = 10)
    private String name;

    /**
     * 所属项目ID
     */
    @NotNull
    private Long projectId;

    /**
     * 计划开始日期
     */
    @NotNull
    private LocalDate planStartDate;

    /**
     * 计划结束日期
     */
    @NotNull
    private LocalDate planEndDate;

    /**
     * 版本开始时间
     */
    private LocalDateTime startTime;

    /**
     * 版本结束时间
     */
    private LocalDateTime endTime;

    /**
     * 版本备注信息
     */
    @NotBlank
    @Length(max = 1500)
    private String remark;

    /**
     * 迭代信息
     */
    @Transient
    private List<ScrumIterationDTO> iterations;

    @Override
    public int compareTo(@Nullable Serial other) {
        int compare = super.compareTo(other);
        if (compare == 0 && other instanceof ScrumVersionDTO) {
            ScrumVersionDTO otherVersion = (ScrumVersionDTO) other;
            compare = planStartDate.compareTo(otherVersion.getPlanStartDate());
            if (compare == 0) {
                compare = getGmtCreate().compareTo(otherVersion.getGmtCreate());
            }
        }
        return compare;
    }
}
