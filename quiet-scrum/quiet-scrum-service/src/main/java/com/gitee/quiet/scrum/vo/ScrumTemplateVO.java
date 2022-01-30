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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 项目模板.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumTemplateVO extends BaseVO {
    
    /**
     * 模板中的任务步骤
     */
    List<ScrumTaskStepVO> taskSteps;
    
    /**
     * 模板中的优先级配置
     */
    List<ScrumPriorityVO> priorities;
    
    /**
     * 模板名称
     */
    @NotBlank
    @Length(max = 10)
    private String name;
    
    /**
     * 是否启用，true：项目可以选择该模板，false：项目新建的时候不可以选择该模块
     */
    private Boolean enabled;
    
    /**
     * 模板备注信息
     */
    @Length(max = 30)
    private String remark;
    
}
