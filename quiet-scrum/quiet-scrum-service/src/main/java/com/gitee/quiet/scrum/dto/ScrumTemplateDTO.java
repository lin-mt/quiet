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

import com.gitee.quiet.service.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 项目模板.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumTemplateDTO extends BaseDTO {

    /**
     * 模板中的任务步骤
     */
    @Transient
    List<ScrumTaskStepDTO> taskSteps;

    /**
     * 模板中的优先级配置
     */
    @Transient
    List<ScrumPriorityDTO> priorities;

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
