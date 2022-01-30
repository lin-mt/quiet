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
