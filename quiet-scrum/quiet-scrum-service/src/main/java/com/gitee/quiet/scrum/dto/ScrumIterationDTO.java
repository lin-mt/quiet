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
import com.gitee.quiet.service.dto.SerialDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 迭代信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumIterationDTO extends SerialDTO {
    
    /**
     * 迭代名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;
    
    /**
     * 所属版本ID
     */
    @NotNull
    private Long versionId;
    
    /**
     * 迭代计划开始日期
     */
    @NotNull
    private LocalDate planStartDate;
    
    /**
     * 迭代计划结束日期
     */
    @NotNull
    private LocalDate planEndDate;
    
    /**
     * 迭代开始时间
     */
    private LocalDateTime startTime;
    
    /**
     * 迭代结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 备注信息
     */
    @Length(max = 1000)
    private String remark;
    
    @Override
    public int compareTo(@Nullable Serial other) {
        int compare = super.compareTo(other);
        if (compare == 0 && other instanceof ScrumIterationDTO) {
            ScrumIterationDTO otherIteration = (ScrumIterationDTO) other;
            compare = planStartDate.compareTo(otherIteration.getPlanStartDate());
            if (compare == 0) {
                compare = getGmtCreate().compareTo(otherIteration.getGmtCreate());
            }
        }
        return compare;
    }
}